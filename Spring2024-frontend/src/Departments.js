import logo from './logo.svg';

import './App.css';

import { DataGrid } from '@mui/x-data-grid';
import axios from 'axios';
import React from 'react';
import { Container, Box, Button, TextField, Grid2 as Grid,
Typography, Paper, FormControl, Select, Menu, MenuItem,
 ListItem, ListItemText, Link, ListItemButton, List } from "@mui/material";

const isSmallScreen = false;
const filters = ["Employees", "Departments" ];

const columns = [
  { field: 'id', flex: 1, headerName: 'ID', width: 100,
      editable: false,
      sortable: false,
      disableColumnMenu: true },
  {
    field: 'department_name',
    flex: 1.2,
    headerName: 'Department',
    width: 150,
    editable: false,
    sortable: false,
    disableColumnMenu: true
  }
];


class Departments extends React.Component {

  constructor() {
    super();
    this.state = {};
  }

  componentDidMount() {
     this.setState({rows: [], dataLoading: true});

     axios.get('/api/department/list').then((data) => {
         this.setState({rows : data.data ? data.data: [], dataLoading: false});
     }).catch((err) => {
        alert(err);
     });
  }

  render () {
      const rows = this.state.rows;

      return (
             <div className="App">
                <Container maxWidth="lg">
                  <Grid container spacing={3}>
                    {/* Header */}
                    <Grid item size={{xs: 12}}>
                      <Paper sx={{ p: 3, textAlign: "center" }}>
                        <Typography variant="h5">
                            Departments
                        </Typography>
                      </Paper>
                    </Grid>
                    {/* Side nav */}
                    <Grid item size={{xs: 12, sm: 6, md:4, lg: 3}}>
                      <Paper sx={{ p: 2 }}>
                         <ListItem button index={1}>
                                <Button href='/#' component={Link} >
                                    Employees
                                </Button >
                         </ListItem>
                         <ListItem button index={2}>
                                <Button variant="outlined" href='/#departments' component={Link}  >
                                    Departments
                                </Button >
                         </ListItem>
                      </Paper>
                    </Grid>
                    {/* Main Content */}
                    <Grid item size={{xs: 12, sm: 6, md:8, lg: 9}}>
                      <Paper sx={{ p: 2 }}>
                         <div style={{height: 500}}>
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

export default Departments;
