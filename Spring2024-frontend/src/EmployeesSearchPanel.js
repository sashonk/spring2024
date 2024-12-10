import React from 'react';
import { Box, Button, TextField } from "@mui/material";
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { AdapterMoment } from '@mui/x-date-pickers/AdapterMoment';

import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { DateField } from '@mui/x-date-pickers/DateField';

import { useForm, Controller } from "react-hook-form";
import { useState } from "react";
import axios from 'axios';
import moment from 'moment';
import ApiClient from "./generated-api/src/ApiClient";
import DefaultApi from "./generated-api/src/api/DefaultApi";


const startOfQ11960 = moment('1960-01-01T00:00:00.000');
const endOfQ41962 = moment('1963-09-30T23:59:59.999');

const startOfQ119602 = moment('1960-01-01T00:00:00.000');
const endOfQ419622 = moment('1963-09-30T23:59:59.999');

let api = new DefaultApi(new ApiClient("/api/v2"));

let EmployeesSearchPanel = function({ updateLoading, setRows }) {

    const { register, handleSubmit, control, formState: { errors } } = useForm();
    const [rows] = useState([]);

    const convertObjToArray = (dataObj) => {
        let dataArr = [];
        for (let key in dataObj) {
            if (dataObj.hasOwnProperty(key)) {
                if (dataObj[key]) {
                    dataArr.push({ name: key, value: dataObj[key] })
                }
            }
        }
        return dataArr;
    }

    const buildQuery = (dataObj) => {
        let query = '';
        let c = 0;
        for (let key in dataObj) {
            if (dataObj.hasOwnProperty(key)) {
                if (dataObj[key]) {
                    if (c++ > 0) {
                        query += "&";
                    }
                    query += key + '=' + dataObj[key];
                }
            }
        }
        return query;
    }

    const onSubmit = (formDataObj) => {
        if (formDataObj.birthDateFrom && 'toDate' in formDataObj.birthDateFrom) {
            formDataObj.birthDateFrom = formDataObj.birthDateFrom.format('yyyy-MM-DD');
        }
        if (formDataObj.birthDateTo && 'toDate' in formDataObj.birthDateTo) {
            formDataObj.birthDateTo = formDataObj.birthDateTo.format('yyyy-MM-DD');
        }
        //alert(JSON.stringify(getFormData(d)));
        const formData = convertObjToArray(formDataObj);
        const query = buildQuery(formDataObj);

        //alert(JSON.stringify(query));
        //const query = 
        /*		axios.get("/api/employee/search?" + query)
                .then((rs) => {
                    console.log('updateLoading')
                    updateLoading(false);
                    console.log('setRows')
                    setRows(rs.data);
                })
                .catch(e => alert(e))	*/


        updateLoading(true);
        let opts = {};
        if (formDataObj.firstName) {
            opts.firstName = formDataObj.firstName;
        }
        if (formDataObj.lastName) {
            opts.lastName = formDataObj.lastName;
        }
        api.search(formDataObj.birthDateFrom, formDataObj.birthDateTo, formDataObj.gender,
            opts,
            (error, data, response) => {

                updateLoading(false);
                setRows(data);
            });
    }

    const sx = { m: 1, width: '200px' };

    return (<form onSubmit={handleSubmit(onSubmit)}>
        <TextField {...register("id")} label="ID" variant="standard" sx={sx} />
        <TextField {...register("firstName")} label="First Name" variant="standard" sx={sx} />
        <TextField {...register("lastName")} label="Last Name" variant="standard" sx={sx} />
        <TextField error={errors.gender} helperText={errors.gender?.message} {...register("gender", { validate: (value) => value == 'M' || value == 'F' || 'M or F' })} label="Gender" variant="standard" sx={sx} />
        <Controller
            control={control}
            name={'birthDateFrom'}
            defaultValue={startOfQ11960}
            rules={{ required: true }}
            render={({ field: { onChange, onBlur, value, ref } }) => (
                <LocalizationProvider dateAdapter={AdapterMoment}>
                    <DatePicker
                        label="Birth Date from"
                        sx={sx}
                        onChange={onChange}
                        onBlur={onBlur}
                        minDate={startOfQ11960}
                        maxDate={endOfQ41962}
                        defaultValue={startOfQ11960}
                        slotProps={{
                            textField: {
                                error: errors.birthDateFrom
                            },
                        }}
                    />
                </LocalizationProvider>
            )}
        />
        <Controller
            control={control}
            name={'birthDateTo'}
            defaultValue={endOfQ41962}
            rules={{ required: true }}
            render={({ field: { onChange, onBlur, value, ref } }) => (
                <LocalizationProvider dateAdapter={AdapterMoment}>
                    <DatePicker
                        label="Birth Date to"
                        sx={sx}
                        onChange={onChange}
                        onBlur={onBlur}
                        minDate={startOfQ11960}
                        maxDate={endOfQ41962}
                        defaultValue={endOfQ41962}
                        slotProps={{
                            textField: {
                                error: errors.birthDateTo
                            },
                        }}
                    />
                </LocalizationProvider>
            )}
        />
        <Button variant='contained' type="submit" sx={{ position: 'relative', m: '25px' }} >Search</Button>
    </form>);
}

export default EmployeesSearchPanel;