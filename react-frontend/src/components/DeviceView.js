// here put all the stuff unique to a specific device
import React from 'react';
import { withRouter } from "react-router";
import { Link, Route } from "react-router-dom";

class DeviceView extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            userId: this.props.match.params.userID,
            deviceId: this.props.match.params.deviceID,
            locationList: []
          }
    }

    componentDidMount() { 
        console.log(this.state.userId);
        this.getData(this.state.userId, this.state.deviceId);
    }

    getData(userId, deviceId) {
        // create a new XMLHttpRequest
        var locs = new XMLHttpRequest()
        
        // get a callback when the server responds
        locs.addEventListener('load', () => {
          // update the state of the component with the result here
          console.log(locs.responseText);
          const locations = JSON.parse(locs.responseText).results;
          this.setState({locationList: locations})
        })
        // open the request with the verb and the url
        locs.open('GET', `http://localhost:8080/locations/deviceId/${deviceId}`)
        // send the request
        locs.send()
    }

    render() {
        
        const listItems = this.state.locationList.map((location) => 
        <li key={location.locationId}>
            Location {location.locationId}:
            <br/>
            Time is {location.locationTime}
            <br/>
            Latitude is {location.locationLat}
            <br/>
            Longitude is {location.locationLon}
        </li>
        );
        
        return (
            <div>
                This is a device view for device id: {this.state.deviceId}
                <ul>{listItems}</ul>
            </div>
          );
    }
}
 
export default withRouter(DeviceView);

