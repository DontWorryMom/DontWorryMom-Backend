import React from 'react';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';
import EmailIcon from '@mui/icons-material/Email';
import PhoneIphoneIcon from '@mui/icons-material/PhoneIphone';
import MoreVertIcon from '@mui/icons-material/MoreVert';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
var config = require("../Config").config;

class NotificationView extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            notificationList: null,
            anchorEl: null,
            menu: false
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
          console.log(notifs.responseText);
          const notifications = JSON.parse(notifs.responseText).results;
          this.setState({notificationList: notifications})
        })
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
                </div>
                
            )
        }
        else {
            return ( <div>There are no notifications to render </div> )
        }
        
    }

}

export default (NotificationView);