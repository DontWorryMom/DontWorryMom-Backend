import React from 'react';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';
import EmailIcon from '@mui/icons-material/Email';
import PhoneIphoneIcon from '@mui/icons-material/PhoneIphone';
import MoreVertIcon from '@mui/icons-material/MoreVert';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';

import FormControl from '@mui/material/FormControl';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormLabel from '@mui/material/FormLabel';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';

var config = require("../Config").config;

class NotificationView extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            notificationList: null,
            anchorEl: null,
            menu: false,

            post_notif_type: "Email",
            post_notif_content: null
        }
        this.notifEdit = React.createRef();
        this.print_notif = this.print_notif.bind(this);
        this.handleClick = this.handleClick.bind(this);
        this.closeMenu = this.closeMenu.bind(this);
    }

    componentDidMount() { 
        this.getData();
    }

    // helper function to retrieve notification method data from backend database
    getData() {
        // create a new XMLHttpRequest
        var notifs = new XMLHttpRequest()
        
        // get a callback when the server responds
        notifs.addEventListener('load', () => {
          // update the state of the component with the result here
          const notifications = JSON.parse(notifs.responseText).results;
          this.setState({notificationList: notifications})
        })

        // added because of login security
        notifs.withCredentials=true;

        // open the request with the verb and the url
        notifs.open('GET', config.SPRING_BACKEND_FULL_URL + `/notifications/userId/${this.props.userId}`)
        // send the request
        notifs.send()
    }

    // helper function to print each notification method with its correct icon
    print_notif(notif) {
        if (notif.type === 'TEXT_NOTIFICATION') {
            return (
                <ListItem>
                    <PhoneIphoneIcon />
                    <ListItemText primary={notif.phoneNumber} />
                    <MoreVertIcon ref={this.notifEdit} onClick={this.handleClick} style={{cursor:'pointer'}} />
                </ListItem>
            )
        }
        else if (notif.type === 'EMAIL_NOTIFICATION') {
            return (
                <ListItem>
                    <EmailIcon />
                    <ListItemText primary={notif.email} />
                    <MoreVertIcon ref={this.notifEdit} onClick={this.handleClick} style={{cursor:'pointer'}} />
                </ListItem>
            )
        }
        else {
            return (
                <ListItem>
                    <ListItemText primary='Unknown Notification Type' />
                </ListItem>
            )
        }
        
    }

    handleRadioChange = event => {
        this.setState({post_notif_type: event.target.value})
    }
    handleTextChange = event => {
        this.setState({post_notif_content: event.target.value.toString()})
    }

    // helper function to create a notification
    create_notif = event => {

        // create a new XMLHttpRequest
        var notif = new XMLHttpRequest()

        // added because of login security
        notif.withCredentials=true;

        // open the request with the verb and the url
        notif.open('POST', config.SPRING_BACKEND_FULL_URL + `/notifications/userId/${this.props.userId}`)
        notif.setRequestHeader("Content-Type", "application/json");
        // send the request
        if (this.state.post_notif_type === "Email") {
            notif.send(
                JSON.stringify({
                    type:"EMAIL_NOTIFICATION",
                    email: this.state.post_notif_content
                })
            )
        }
        else {
            notif.send(
                JSON.stringify({
                    type:"TEXT_NOTIFICATION",
                    phoneNumber: this.state.post_notif_content
                })
            )
        }

        // get a callback when the server responds
        notif.addEventListener('load', () => {
            // update the state of the component with the result here
            const create_notif_response = JSON.parse(notif.responseText).results;
            var new_notif = this.state.notificationList.concat(create_notif_response)
            this.setState({notificationList: new_notif})
        })
        
        
        
    }

    handleClick() {
        console.log("Edit function called")
        this.setState({menu: true})
        this.setState({anchorEl: this.notifEdit.current})
    }

    closeMenu() {
        this.setState({menu: false})
        this.setState({anchorEl: null})
    }

    render() {
        
        if (this.state.notificationList != null)
        {
            return (
                <div>Active Notifications
                    <List sx={{ width: '100%', maxWidth: 360, bgcolor: 'background.paper' }}>
                        {this.state.notificationList.map(this.print_notif)}
                    </List>
                    <Menu
                        id="test"
                        aria-labelledby="test"
                        anchorEl={this.state.anchorEl}
                        open={this.state.menu}
                        onClose={this.closeMenu}
                        anchorOrigin={{
                        vertical: 'top',
                        horizontal: 'left',
                        }}
                        transformOrigin={{
                        vertical: 'top',
                        horizontal: 'left',
                        }}
                    >
                        <MenuItem onClick={this.closeMenu}>Edit</MenuItem>
                        <MenuItem onClick={this.closeMenu}>Remove</MenuItem>
                    </Menu>

                    <form>
                        <FormControl >
                            <FormLabel id="add-notification">Type</FormLabel>
                            <RadioGroup
                                aria-labelledby="notification-type"
                                name="notification-type"
                                value={this.state.post_notif_type}
                                onChange={this.handleRadioChange}
                            >
                                <FormControlLabel value="Email" control={<Radio />} label="Email" />
                                <FormControlLabel value="Phone Number" control={<Radio />} label="Phone Number" />
                            </RadioGroup>

                            <TextField 
                                id="standard-basic" 
                                label="Add Notification" 
                                required 
                                variant="standard" 
                                helperText="required field"
                                onChange={this.handleTextChange} 
                            />

                            <Button onClick={this.create_notif} sx={{ mt: 1, mr: 1 }} variant="outlined">
                                Add
                            </Button>

                        </FormControl>
                    </form>

                </div>
                
            )
        }
        else {
            return ( <div>There are no notifications to render </div> )
        }
        
    }

}

export default (NotificationView);