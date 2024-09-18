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


class App extends React.Component {

  constructor() {
    super();
    this.state = {};
  }

  componentDidMount() {
     this.setState({rows: []});
  }

  doSearch() {
       if(!this.state.search) {
        alert('Text is empty');
        return;
       }

       this.setState({dataLoading: true});
       axios.get('/api/employee/findBy?str=' + this.state.search).then((data) => {
            this.setState({rows : data.data ? data.data: [], dataLoading: false});
        }).catch((err) => {
          alert(err);
        });
  }

  updateSearchText(evt){
    this.setState({search: evt.target.value});
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
                            Employees
                        </Typography>
                      </Paper>
                    </Grid>
                    {/* Side nav */}
                    <Grid item size={{xs: 12, sm: 6, md:4, lg: 3}}>
                      <Paper sx={{ p: 2 }}>
                         <ListItem button index={1}>
                                <Button variant="outlined" href='/#' component={Link}  >
                                    Employees
                                </Button >
                         </ListItem>
                         <ListItem button index={2}>
                                <Button href='/#departments'  component={Link}  >
                                    Departments
                                </Button >
                         </ListItem>
                      </Paper>
                    </Grid>
                    {/* Main Content */}
                    <Grid item size={{xs: 12, sm: 6, md:8, lg: 9}}>
                      <Paper sx={{ p: 2 }}>
                        <Box component='form'
                          sx={{ '& > :not(style)': { m: 1 } }}
                        >
                          <TextField value={this.state.search} onChange={this.updateSearchText.bind(this)} id="standard-basic" label="Text" variant="standard" />
                          <Button onClick={this.doSearch.bind(this)} >Search</Button>
                        </Box >
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


export default App;
