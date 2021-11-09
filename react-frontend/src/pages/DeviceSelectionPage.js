import React from 'react';
import { withRouter } from "react-router";
import { Link, Route } from "react-router-dom";
import DeviceView from '../components/DeviceView';

class DeviceSelectionPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            // initialize local state here
            userId: this.props.match.params.userID,
            devices: []
        };
        // ... other stuff needed for initialization
    }

    

    componentDidMount() {
        console.log('User id is ' + this.state.userId);
        this.getData(this.state.userId);
    }
    
    
    getData(id) {
        // create a new XMLHttpRequest
        var req = new XMLHttpRequest()
        
        // get a callback when the server responds
        req.addEventListener('load', () => {
          // update the state of the component with the result here
          console.log(req.responseText);
          const results = JSON.parse(req.responseText).results;
          this.setState({devices: results})
        })
        // open the request with the verb and the url
        req.open('GET', window.DB_BASE_URL + `/devices/userId/${id}`)
        // send the request
        req.send()
    }
    
    render() {
        const deviceLength = this.state.devices.length;

        const listItems = this.state.devices.map((device) => 
        <li key={device.deviceId}>
            <Link to={`${this.state.userId}/${device.deviceId}`}>Info for device {device.deviceId}</Link>
            <Route path={`${this.state.userID}/${device.deviceId}`}>
                <DeviceView />
            </Route>
        </li>
        );

        return (
            <div>
                <div>Num of Devices is {deviceLength}</div>
                <ul>{listItems}</ul>
            </div>
        );
        // ... code to render page
    }
}

export default withRouter(DeviceSelectionPage);