import logo from './logo.svg';

import './App.css';
import Test from './Test';

import { DataGrid } from '@mui/x-data-grid';
import Button from '@mui/material/Button';
import axios from 'axios';
import React from 'react';

const columns = [
  { field: 'id', headerName: 'ID', width: 190 },
  {
    field: 'firstName',
    headerName: 'First Name',
    width: 250
  },
  {
      field: 'lastName',
      headerName: 'Last Name',
      width: 250
  },
  {
      field: 'gender',
      headerName: 'Gender',
      width: 80
  },
  {
        field: 'birthDate',
        type: 'date',
        valueGetter: (value) => value && new Date(value),
        headerName: 'Birth date',
        width: 200
  }
];

class App extends React.Component {

  constructor() {
    super();
    this.state = {};
  }

  componentDidMount() {
     this.setState({rows: []});
     axios.get('/api/employee/find').then((data) => {

          console.log(data);

          this.setState({rows : data.data ? data.data: []});
      }).catch((err) => {
        alert(err);
      });
  }

  render () {
      const rows = this.state.rows;

      return (
             <div className="App">
                <h1>Employee DataGrid</h1>
                 <DataGrid
                    rows={rows}
                    columns={columns}
                 >
                 </DataGrid>
             </div>
           );
    }
}


export default App;
