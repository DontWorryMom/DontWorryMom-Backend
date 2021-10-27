import React from 'react';
import { Link, Route } from "react-router-dom";
import DeviceSelectionPage from './DeviceSelectionPage';

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
        req.open('GET', 'http://localhost:8080/users/')
        // send the request
        req.send()
    }
    
    render() {
        const userLength = this.state.users.length;

        const listItems = this.state.users.map((user) => 
            <li key={user.userId}>
                <Link to={`devices/user/${user.userId}`}>{user.username}'s Devices</Link>
                <Route path='devices/user/:userID'>
                    <DeviceSelectionPage />
                </Route>
            </li>
            );
        

        return (
            <div>
                <div>There are currently {userLength} users</div>
                <ul>{listItems}</ul>
            </div>
        );
        // ... code to render page
    }
}

export default (UserSelectionPage);