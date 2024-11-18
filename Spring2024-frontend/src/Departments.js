import logo from './logo.svg';

import { DataGrid } from '@mui/x-data-grid';
import axios from 'axios';
import React from 'react';
import Header from './Header';
import SideBar from './SideBar';
import sidebarConfig from './sidebarConfig';

import { Container, Grid2 as Grid, Paper, Link } from "@mui/material";
import ApiClient from "./generated-api/src/ApiClient";
import DefaultApi from "./generated-api/src/api/DefaultApi";

const isSmallScreen = false;
const filters = ["Employees", "Departments"];

const columns = [
	{
		field: 'id', flex: 1, headerName: 'ID', width: 100,
		editable: false,
		sortable: false,
		disableColumnMenu: true
	},
	{
		field: 'deptName',
		flex: 1.2,
		headerName: 'Department',
		width: 150,
		editable: false,
		sortable: false,
		disableColumnMenu: true
	}
];

let api = new DefaultApi(new ApiClient("/api/v2"));

class Departments extends React.Component {

	constructor() {
		super();
		this.state = {};
	}

	componentDidMount() {
		this.setState({ rows: [], dataLoading: true });


		api.getDepartments((error, data, response) => {
			console.log(error);
			this.setState({ rows: data, dataLoading: false });
		});

		/*     axios.get('/api/department/list').then((data) => {
				 this.setState({rows : data.data ? data.data: [], dataLoading: false});
			 }).catch((err) => {
				alert(err);
			 });*/
	}

	render() {
		const rows = this.state.rows;

		return (
			<div className="App">
				<Container maxWidth="lg">
					<Grid container spacing={3}>
						{/* Header */}
						<Header title="DEPARTMENTS" />
						{/* Side nav */}
						<SideBar config={sidebarConfig} activeId="deparments" />
						{/* Main Content */}
						<Grid item size={{ xs: 12, sm: 6, md: 8, lg: 9 }}>
							<Paper sx={{ p: 2 }}>
								<div style={{ height: 500 }}>
									<DataGrid
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

export default Departments;
