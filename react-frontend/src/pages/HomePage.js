import React from 'react';
import dont_worry_mom_gif from '../resources/dont-worry-mom.gif';

import Box from '@mui/material/Box';
import Typography from "@mui/material/Typography";

import Login from '../components/login'

import Navbar from '../components/Navbar';

class HomePage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            // initialize local state here
            rerender: false
        };
        // ... other stuff needed for initialization
        this.rerenderHomePage = this.rerenderHomePage.bind(this);
    }

    rerenderHomePage() {
        window.location.reload(false);
    }

    render() {
        return (
            <header className="App-header">

                <Navbar />

                <Box sx={{ display: 'grid', gridTemplateColumns: 'repeat(2, 1fr)' }}>
                    <Box sx={{m:2}}>

                        <img src={dont_worry_mom_gif} className="DWM-logo" alt="Don't Worry Mom" />

                        <Typography
                            variant="h3"
                            component="div"
                            align='center'
                            sx={{ mr: 2, display: { xs: "none", md: "flex" } }}
                        >
                        
                            <Box sx={{textAlign:'left'}}>Don't Worry Mom</Box>
                        </Typography>

                        <Typography
                            variant="h5"
                            component="div"
                            align='center'
                            sx={{ mr: 2, display: { xs: "none", md: "flex" } }}
                        >
                        
                            <Box sx={{textAlign:'left'}}>A Fall Detection Device</Box>
                        </Typography>

                    </Box>

                    <Login rerenderHomePage={this.rerenderHomePage}/>
                    
                </Box>
            </header>
        );
    }
}

export default (HomePage);