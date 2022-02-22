import React from 'react';
import {Link} from 'react-router-dom';

import AppBar from '@mui/material/AppBar';
import Container from '@mui/material/Container';
import Toolbar from '@mui/material/Toolbar';
import Box from '@mui/material/Box';
import IconButton from '@mui/material/IconButton';
import HomeIcon from '@mui/icons-material/Home';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';

var config = require("../Config").config;

class Navbar extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            loggedin: false,
            userId: null
        }
    }

    componentDidMount() { 
        this.checkLogin();
    }

    checkLogin() {

        // First try to get the current user
        var user = new XMLHttpRequest();
        user.withCredentials=true;
        user.addEventListener('load', () => {
            // update the state of the component with the result here
            if (user.status === 200 && user.responseText[0] !== '<')
            {
                var userId = JSON.parse(user.responseText).results.userId;
                this.setState({loggedin: true});
                this.setState({userId: userId});
            }
            else
            {
                this.setState({loggedin: false});
            }
            });
        user.open('GET', config.SPRING_BACKEND_FULL_URL + '/users/currentUser');
        user.send();


    }

    render() {
        
        return (
            <AppBar position='static'>
                <Container maxWidth='xl'>
                    <Toolbar disableGutters>
                        <Box sx={{ flexGrow: 1}}>
                            <Link to='/'>
                                <IconButton><HomeIcon/></IconButton>
                            </Link>
                        </Box>

                        <Box sx={{ flexGrow: 1}}>
                            {this.state.loggedin && 
                                <Link to={`/devices/user/${this.state.userId}`}>Device List</Link>
                            }
                            
                        </Box>

                        <Box sx={{ display: 'flex', flexDirection: 'row', p: 1, m: 1}}>
                            {this.state.loggedin ? <p>Logged In</p> : <p>Please Log In</p>}
                            <AccountCircleIcon/>
                        </Box>

                    </Toolbar>
                </Container>
            </AppBar>
          );
    }
}
 
export default Navbar;