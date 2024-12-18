import logo from './logo.svg';


import { DataGrid } from '@mui/x-data-grid';
import axios from 'axios';
import React from 'react';
import Header from './Header';
import SideBar from './SideBar';
import EmployeesSearchPanel from './EmployeesSearchPanel';
import sidebarConfig from './sidebarConfig';

import {
	Container, Box, Button, TextField, Grid2 as Grid,  Paper
} from "@mui/material";


const columns = [
	{
		field: 'id', flex: 1, headerName: 'ID', width: 100,
		editable: false,
		sortable: false,
		disableColumnMenu: true
	},
	{
		field: 'firstName',
		flex: 1.2,
		headerName: 'First Name',
		width: 150,
		editable: false,
		sortable: false,
		disableColumnMenu: true
	},
	{
		field: 'lastName',
		flex: 1.5,
		headerName: 'Last Name',
		width: 150,
		editable: false,
		sortable: false,
		disableColumnMenu: true
	},
	{
		field: 'gender',
		flex: 1,
		headerName: 'Gender',
		width: 150,
		editable: false,
		sortable: false,
		disableColumnMenu: true
	},
	{
		field: 'birthDate',
		flex: 1,
		type: 'date',
		valueGetter: (value) => value && new Date(value),
		headerName: 'Birth date',
		editable: false,
		sortable: false,
		disableColumnMenu: true
	}
];


class Employees extends React.Component {

	constructor() {
		super();
		this.state = {};
	}

	componentDidMount() {
		this.setState({ rows: [] });
	}

	doSearch() {
		if (!this.state.search) {
			alert('Text is empty');
			return;
		}

		this.setState({ dataLoading: true });
		axios.get('/api/employee/findBy?str=' + this.state.search).then((data) => {
			this.setState({ rows: data.data ? data.data : [], dataLoading: false });
		}).catch((err) => {
			alert(err);
		});
	}
	
	doSetRows(rows) {
		this.setState({rows})
		console.log('logs were set to: ' + rows)
	}

	updateSearchText(evt) {
		this.setState({ search: evt.target.value });
	}
	
	doUpdateLoading(dataLoading) {
		this.setState({dataLoading})
	}

	render() {
		const rows = this.state.rows;

		return (
			<div className="App">
				<Container maxWidth="lg">
					<Grid container spacing={3}>
						{/* Header */}
						<Header title="EMPLOYEES" />
						{/* Side nav */}
						<SideBar config={sidebarConfig} activeId="employees" />
						{/* Main Content */}
						<Grid item size={{ xs: 12, sm: 6, md: 8, lg: 9 }}>
							<Paper sx={{ p: 2 }}>
								<EmployeesSearchPanel updateLoading={this.doUpdateLoading.bind(this)}  setRows={this.doSetRows.bind(this)} />											
								<div style={{ height: 500 }}>
									<DataGrid
										keepMounted={true}
										rows={rows}
										columns={columns}
										loading={this.state.dataLoading}
									>
									</DataGrid>
								</div>
							</Paper>
						</Grid>
					</Grid>
				</Container>
			</div>

		);
	}
}


export default Employees;
