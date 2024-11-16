import React from 'react';
import { Box, Button, TextField } from "@mui/material";
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { AdapterMoment } from '@mui/x-date-pickers/AdapterMoment';

import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { DateField } from '@mui/x-date-pickers/DateField';

import { useForm, Controller } from "react-hook-form";
import {useState} from "react";
import axios from 'axios';

let EmployeesSearchPanel = function({updateLoading, setRows}) {

	const { register, handleSubmit, control } = useForm();
	const [rows] = useState([]);

	const convertObjToArray = (dataObj) => {
		let dataArr = [];
	    for (let key in dataObj) {
			if (dataObj.hasOwnProperty(key)) {
				if (dataObj[key]) {
					dataArr.push({name: key, value: dataObj[key]})
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
			formDataObj.birthDateFrom = formDataObj.birthDateFrom.toDate().toISOString().slice(0, 10);
		}
		if (formDataObj.birthDateTo && 'toDate' in formDataObj.birthDateTo) {
			formDataObj.birthDateTo = formDataObj.birthDateTo.toDate().toISOString().slice(0, 10);
		}
		//alert(JSON.stringify(getFormData(d)));
		const formData = convertObjToArray(formDataObj);
		const query = buildQuery(formDataObj);

		//alert(JSON.stringify(query));
		//const query = 
		updateLoading(true);
		axios.get("/api/employee/search?" + query)
		.then((rs) => {
			console.log('updateLoading')
			updateLoading(false);
			console.log('setRows')
			setRows(rs.data);
		})
		.catch(e => alert(e))		
	}

	const sx = { m: 1, width: '200px' };

	return (<form onSubmit={handleSubmit(onSubmit)}>
		<TextField  {...register("id")} label="ID" variant="standard" sx={sx} />
		<TextField  {...register("firstName")} label="First Name" variant="standard" sx={sx} />
		<TextField {...register("lastName")} label="Last Name" variant="standard" sx={sx} />
		<TextField {...register("gender")} label="Gender" variant="standard" sx={sx} />
		<Controller
			control={control}
			name={'birthDateFrom'}
			render={({ field: { onChange, onBlur, value, ref } }) => (
				<LocalizationProvider dateAdapter={AdapterMoment}>
					<DatePicker
						label="Birth Date from"
						sx={sx}
						onChange={onChange}
						onBlur={onBlur}
					/>
				</LocalizationProvider>
			)}
		/>
		<Controller
			control={control}
			name={'birthDateTo'}
			render={({ field: { onChange, onBlur, value, ref } }) => (
				<LocalizationProvider dateAdapter={AdapterDayjs}>
					<DatePicker
						label="Birth Date to"
						sx={sx}
						onChange={onChange}
						onBlur={onBlur}
					/>
				</LocalizationProvider>
			)}
		/>
		<Button variant='contained' type="submit" sx={{ position: 'relative', m: '25px' }} >Search</Button>
	</form>);
}

export default EmployeesSearchPanel;