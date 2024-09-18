import logo from './logo.svg';

import './App.css';
import Test from './Test';

import { DataGrid } from '@mui/x-data-grid';
import Button from '@mui/material/Button';
import axios from 'axios';
import React from 'react';
import { Container, Box, TextField, Grid, Typography, Paper, FormControl, Select, MenuItem, ListItem, ListItemText, List } from "@mui/material";


const isSmallScreen = false;
const filters = ["Filter 1", "Filter 2", "Filter 3"];

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

       this.setState({gridLoading: true});
       axios.get('/api/employee/findBy?str=' + this.state.search).then((data) => {
            this.setState({rows : data.data ? data.data: [], gridLoading: false});
        }).catch((err) => {
          alert(err);
        });
  }

  updateSearchText(evt){
    console.log(evt);

    this.setState({search: evt.target.value});
  }

  render () {
      const rows = this.state.rows;

      return (
             <div className="App">
                <Container maxWidth="lg">
                  <Grid container spacing={3}>
                    {/* Header */}
                    <Grid item xs={12}>
                      <Paper sx={{ p: 3, textAlign: "center" }}>
                        <Typography variant="h4">
                            Employees
                        </Typography>
                      </Paper>
                    </Grid>
                    {/* sidenav */}
                    <Grid item xs={12} sm={4} md={3} lg={2}>
                      <Paper sx={{ p: 2 }}>
                        {isSmallScreen ? (
                          <FormControl fullWidth>
                            <Select>
                              {filters.map((filter, index) => (
                                <MenuItem key={index} value={filter}>
                                  {filter}
                                </MenuItem>
                              ))}
                            </Select>
                          </FormControl>
                        ) : (
                          <List>
                            {filters.map((filter, index) => (
                              <ListItem button key={index}>
                                <ListItemText primary={filter} />
                              </ListItem>
                            ))}
                          </List>
                        )}
                      </Paper>
                    </Grid>
                    {/* Main Content */}
                    <Grid item xs={12} sm={8} md={9} lg={10}>
                      <Paper sx={{ p: 2 }}>
                        <Box component='form'
                        sx={{ '& > :not(style)': { m: 1 } }}
                        >
                          <TextField value={this.state.search} onChange={this.updateSearchText.bind(this)} id="standard-basic" label="Text" variant="standard" />
                          <Button onClick={this.doSearch.bind(this)} >Search</Button>
                        </Box >
                         <div style={{height: 500}}>
                         <DataGrid
                            rows={rows}
                            columns={columns}
                            loading={this.state.gridLoading}
                            >
                         </DataGrid>
                         </div>
                      </Paper>
                    </Grid>
                    {/* Footer */}
                    <Grid item xs={12}>
                      <Paper sx={{ p: 2, textAlign: "center" }}>
                        <Typography variant="body1">&copy; 2024 Kisorzhevskiy</Typography>
                      </Paper>
                    </Grid>
                  </Grid>
                </Container>
             </div>




           );
    }
}


export default App;
