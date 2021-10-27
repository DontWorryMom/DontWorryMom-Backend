import React from 'react';
import { withRouter } from "react-router";

class DeviceSelectionPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            // initialize local state here
            userID: null,
            devices: []
        };
        // ... other stuff needed for initialization
    }

    

    componentDidMount() {
        const userId = this.props.match.params.userID;
        console.log(userId);
        this.getData(userId);
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
        req.open('GET', `http://localhost:8080/devices/userId/${id}`)
        // send the request
        req.send()
    }
    
    render() {
        const deviceLength = this.state.devices.length;
        const listItems = this.state.devices.map((device) => 
            <li key={device.deviceId}>
                {device.deviceId}
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