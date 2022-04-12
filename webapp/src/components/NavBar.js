import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import ThermostatIcon from '@mui/icons-material/Thermostat';
import {Link} from 'react-router-dom';
export default function NavBar() {
  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static">
        <Toolbar>
          <IconButton
            size="large"
            edge="start"
            color="inherit"
            aria-label="label"
            sx={{ mr: 4 }}
          >
            <ThermostatIcon />
        <Typography variant="h5" component="div" sx={{ flexGrow: 2 }}>
          TempCheck
        </Typography>
          </IconButton>
          <Button component={Link} to='/' color='inherit'>Home</Button>
          <Button component={Link} to='/login' color='inherit'>Login</Button>
          <Button component={Link} to='/register' color='inherit'>Register</Button>
          <Button component={Link} to='/upload' color='inherit'>Upload</Button>
        </Toolbar>
      </AppBar>
    </Box>
  );
}
