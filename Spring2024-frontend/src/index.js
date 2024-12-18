import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import Employees from './Employees';
import Departments from './Departments';
import reportWebVitals from './reportWebVitals';
import {
  createHashRouter,
  RouterProvider,
} from "react-router-dom";

XMLHttpRequest.prototype.doSetRequestHeader = XMLHttpRequest.prototype.setRequestHeader;
XMLHttpRequest.prototype.setRequestHeader = function (name, value) {
	if (typeof name == 'string' && name.toLowerCase().indexOf('user-agent') >= 0) {
		console.log('Prevent setting User-Agent header on XMLHttpRequest');
		return this.doSetRequestHeader('NoUserAgent', value);
	}
	else {
		return this.doSetRequestHeader(name, value);
	}
}

const employees = <Employees/>;
const departments = <Departments/>;

const router = createHashRouter([
  {
	  path: "/",
	  element: employees,
  },
  {
    path: "employees",
    element: employees,
  },
  {
      path: "departments",
      element: departments,
  },
]);

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);

