import React from 'react';
import { Link } from "react-router-dom";
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import PersonIcon from '@mui/icons-material/Person';
var config = require("../Config").config;

class UserSelectionPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            // initialize local state here
            users: []
        };
        // ... other stuff needed for initialization
    }

    componentDidMount() {
        this.getData()
    }
    
    
    getData() {
        // create a new XMLHttpRequest
        var req = new XMLHttpRequest()
        
        // get a callback when the server responds
        req.addEventListener('load', () => {
          // update the state of the component with the result here
          const results = JSON.parse(req.responseText).results;
          this.setState({users: results})
        })
        // open the request with the verb and the url
        req.open('GET', config.SPRING_BACKEND_FULL_URL + '/users/')
        // send the request
        req.send()
    }
    
    render() {
        const userLength = this.state.users.length;

        return (
            <Box
                sx={{
                    //add styling here
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: { xs: 'center'},
                }}
            >
                <div>
                    <div>There are currently {userLength} users</div>
                </div>
                <Box sx={{ flexGrow: 1 }}>
                        <Grid container spacing={6}>
                            {this.state.users.map((user) => 
                                <Grid item xs={4} sx={{
                                                        //add styling here
                                                        display: 'flex',
                                                        flexDirection: 'column',
                                                        alignItems: { xs: 'center'},
                                                    }}>
                                    <PersonIcon />
                                    <br />
                                    <Link to={`devices/user/${user.userId}`}>{user.username}'s Devices</Link>
                                </Grid>
                            
                            )} 
                            
                        </Grid>
                    </Box>
            </Box>
        );
        // ... code to render page
    }
}

export default (UserSelectionPage);