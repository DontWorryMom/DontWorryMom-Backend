import React from 'react';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';
import EmailIcon from '@mui/icons-material/Email';
import PhoneIphoneIcon from '@mui/icons-material/PhoneIphone';
var config = require("../Config").config;

class NotificationView extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            notificationList: null
        }
        
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
                </ListItem>
            )
        }
        else if (notif.type === 'EMAIL_NOTIFICATION') {
            return (
                <ListItem>
                    <EmailIcon />
                    <ListItemText primary={notif.email} />
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

    render() {
        
        if (this.state.notificationList != null)
        {
            return (
                <div>Active Notifications
                    <List sx={{ width: '100%', maxWidth: 360, bgcolor: 'background.paper' }}>
                        {this.state.notificationList.map(this.print_notif)}
                    </List>
                </div>
                
            )
        }
        else {
            return ( <div>There are no notifications to render </div> )
        }
        
    }

}

export default (NotificationView);