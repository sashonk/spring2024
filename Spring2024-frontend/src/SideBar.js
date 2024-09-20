import React from 'react';
import { Grid2 as Grid, Paper, ListItem, Button, ListItemIcon, ListItemText, List, ListItemButton, ListItemAvatar, Avatar} from "@mui/material";
import FolderIcon from '@mui/icons-material/Folder';
import ApartmentIcon from '@mui/icons-material/Apartment';

let SideBar = ({config, activeId}) => {

	if (activeId) {
		config.forEach((item) => {
			if (item.id === activeId) {
				item.props.variant = 'outlined';
			}
		})
	}
	
	const sx={ width: 48, height: 48 };
	
	
	return (
		<Grid item size={{ xs: 12, sm: 6, md: 4, lg: 3}}>
			<Paper sx={{ p: 2 }}>
				<nav aria-label="main mailbox folders">
				<List disableGutters>
					{config.map((item) => {
						return (
							<ListItem disableGutters index={item.index}>
								<ListItemButton variant='contained' {...item.props}>
									<ListItemIcon >
									  {React.createElement(item.icon, {sx})}
									</ListItemIcon>
								<ListItemText primary={item.text} secondary={item.text} />
								</ListItemButton>	
							</ListItem>
						)				
					})}	
				</List>	
				</nav>
			</Paper>
		</Grid>
	)
}


export default SideBar;