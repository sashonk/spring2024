import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import Departments from './Departments';
import reportWebVitals from './reportWebVitals';
import {
  createHashRouter,
  RouterProvider,
} from "react-router-dom";

const router = createHashRouter([
  {
    path: "/",
    element: <App/>,
  },
  {
      path: "departments",
      element: <Departments/>,
  },
]);

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);

