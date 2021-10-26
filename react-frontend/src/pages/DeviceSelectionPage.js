import React from 'react';

class DeviceSelectionPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            // initialize local state here
            devices: []
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
          console.log(req.responseText);
          this.setState({devices: req.responseText})
        })
        // open the request with the verb and the url
        req.open('GET', 'http://localhost:8080/devices/userId/')
        // send the request
        req.send()
    }
    
    render() {
        const deviceLength = this.state.devices.length;
        const listItems = this.state.devices.map((device) => <li>{device}</li>);

        return (
            <div>
                {/*Hardcoded this temporarily*/}
                <div>Device 1</div>
                <div>Device 2</div>
                <div>Num of Devices is {deviceLength}</div>
                <ul>{listItems}</ul>
            </div>
        );
        // ... code to render page
    }
}

export default (DeviceSelectionPage);