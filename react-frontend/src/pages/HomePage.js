import React from 'react';
import dont_worry_mom_gif from '../resources/dont-worry-mom.gif';

import Box from '@mui/material/Box';

import Login from '../components/login'

class HomePage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            // initialize local state here
        };
        // ... other stuff needed for initialization
    }

    render() {
        return (
            <header className="App-header">
                <Box sx={{ display: 'grid', gridTemplateColumns: 'repeat(2, 1fr)' }}>
                    <div>
                        <img src={dont_worry_mom_gif} className="DWM-logo" alt="Don't Worry Mom" />
                        <p>
                            Don't Worry Mom
                        </p>
                    </div>

                    <Login />
                    
                </Box>
            </header>
        );
    }
}

export default (HomePage);