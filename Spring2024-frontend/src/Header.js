import React from 'react';
import { Grid2 as Grid, Paper, Typography } from "@mui/material";
 
let Header = (props) => {
	return (
		<Grid item size={{ xs: 12 }}>
			<Paper sx={{ p: 3, textAlign: "center" }}>
				<Typography variant="h5">
					{props.title}
				</Typography>
			</Paper>
		</Grid>
	);
}


export default Header;