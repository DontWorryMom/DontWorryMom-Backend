import React from 'react';

import { Redirect } from "react-router-dom";

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
            password: null,
            userId: null,
            redirect: false
        }

        this.login = this.login.bind(this)
        this.checkLogin = this.checkLogin.bind(this)
    }


    handleUserChange = event => {
        this.setState({username: event.target.value.toString()})
    }
    handlePassChange = event => {
        this.setState({password: event.target.value.toString()})
    }

    login() {

        // create a new XMLHttpRequest
        var login = new XMLHttpRequest();

        //var payload = "username="+this.state.username+"&password="+this.state.password;
        var payload = "username="+escape(encodeURI(this.state.username))+"&password="+escape(encodeURI(this.state.password));

        // added because of login security
        login.withCredentials=true;

        login.addEventListener('load', () => {
            // update the state of the component with the result here
            console.log(login.responseText);
            console.log(login.status);
            this.checkLogin(login.responseText, login.status)
          })

        // open the request with the verb and the url
        login.open('POST', config.SPRING_BACKEND_FULL_URL + '/login')
        login.setRequestHeader("Content-Type", 'application/x-www-form-urlencoded');
        // send the request
        login.send(payload)

        console.log("Plz work");

        
    }

    // helper function to verify a login
    checkLogin(response, status) {
        if (response === '' && status === 200)
        {
            console.log('in here');
            var user = new XMLHttpRequest();
            user.withCredentials=true;
            user.addEventListener('load', () => {
                // update the state of the component with the result here
                var userId = JSON.parse(user.responseText).results.userId;
                console.log(user.status);
                this.setState({userId: userId})
                this.setState({redirect: true})
                console.log(`devices/user/${userId}`)


              });
            user.open('GET', config.SPRING_BACKEND_FULL_URL + '/users/currentUser');
            user.send();
            
            //this.props.history.push(`devices/user/${user.userId}`)
        }
    }

    logout() {
        console.log("Logged Out")

        // create a new XMLHttpRequest
        var logout = new XMLHttpRequest()

        // added because of login security
        logout.withCredentials=true;

        // open the request with the verb and the url
        logout.open('POST', config.SPRING_BACKEND_FULL_URL + '/logout')
        logout.setRequestHeader("Content-Type", "application/json");
        // send the request
        logout.send()

    }

    render() {

        if (this.state.redirect)
        {
            return(<Redirect to={`devices/user/${this.state.userId}`}/>)
        }

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