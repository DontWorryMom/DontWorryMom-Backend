import React from 'react';
import dont_worry_mom_gif from '../resources/dont-worry-mom.gif';

import Box from '@mui/material/Box';

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
                    <div>
                        <img src={dont_worry_mom_gif} className="DWM-logo" alt="Don't Worry Mom" />
                        <p>
                            Don't Worry Mom
                        </p>
                    </div>

                    <Login rerenderHomePage={this.rerenderHomePage}/>
                    
                </Box>
            </header>
        );
    }
}

export default (HomePage);