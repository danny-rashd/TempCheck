import React from 'react'
import { Container,Toolbar,Typography } from '@mui/material'
import { Navbar } from 'react-bootstrap';
import { AppBar } from '@mui/material'
function Footer() {
    return (
        <Navbar fixed='bottom'>
        <AppBar position="static" color="primary">
          <Container maxWidth="md">
            <Toolbar>
              <Typography variant="body1" color="inherit">
                &copy; 2022 Danny
              </Typography>
            </Toolbar>
          </Container>
        </AppBar>
        </Navbar>
    )
}

export default Footer