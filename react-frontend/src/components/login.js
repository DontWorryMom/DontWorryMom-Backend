import React from 'react';

import Grid from '@mui/material/Grid';
import Paper from '@mui/material/Paper';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';

var config = require("../Config").config;

class Login extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            username: null,
            password: null
        }

        this.login = this.login.bind(this)
    }

    handleUserChange = event => {
        this.setState({username: event.target.value.toString()})
    }
    handlePassChange = event => {
        this.setState({password: event.target.value.toString()})
    }

    login() {

        console.log(this.state.username)

        // create a new XMLHttpRequest
        var login = new XMLHttpRequest();

        //var payload = "username="+this.state.username+"&password="+this.state.password;
        var payload = "username="+escape(encodeURI(this.state.username))+"&password="+escape(encodeURI(this.state.password));

        login.withCredentials=true;

        login.addEventListener('load', () => {
            // update the state of the component with the result here
            console.log(login.responseText);
          })

        // open the request with the verb and the url
        login.open('POST', config.SPRING_BACKEND_FULL_URL + '/login')
        login.setRequestHeader("Content-Type", 'application/x-www-form-urlencoded');
        // send the request
        login.send(payload)

        console.log("Plz work")
    }

    logout() {
        console.log("Logged Out")

        // create a new XMLHttpRequest
        var logout = new XMLHttpRequest()

        // open the request with the verb and the url
        logout.open('POST', config.SPRING_BACKEND_FULL_URL + '/logout')
        logout.setRequestHeader("Content-Type", "application/json");
        // send the request
        logout.send()

    }

    render() {
        return (
            <form>
                <Paper elevation='10' 
                    sx={{ padding:10, height:'50vh', width:400, margin:'5px auto' }}>
                    <Grid align='center'>
                        <p>Please Log In Below</p>
                    </Grid>

                    <TextField 
                        label='Username'
                        variant='standard' 
                        fullWidth 
                        required
                        onChange={this.handleUserChange}
                    />
                    <TextField 
                        label='Password'
                        type='password' 
                        variant='standard' 
                        fullWidth 
                        required
                        onChange={this.handlePassChange}
                    />
                    <p></p>
                    <Button onClick={this.login} variant='contained' fullWidth>Log In</Button>
                    <p></p>
                    <Button onClick={this.logout} variant='outlined' fullWidth>Log Out</Button>

                    <p>{this.state.username}</p>
                    <p>{this.state.password}</p>

                </Paper>
            </form>
        )
    }
}

export default (Login);