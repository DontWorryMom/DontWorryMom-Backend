// here put all the stuff unique to a specific device
import React from 'react';
import { withRouter } from "react-router";

import LocationView from './LocationView';
import NotificationView from './NotificationView';


class DeviceView extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            userId: this.props.match.params.userID,
            deviceId: this.props.match.params.deviceID,
            locationList: [],
            dataReady: false
          }
    }

    componentDidMount() { 
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
          this.setState({dataReady: true});
        })
        // open the request with the verb and the url
        locs.open('GET', window.DB_BASE_URL + `/locations/deviceId/${deviceId}`)
        // send the request
        locs.send()
    }


    render() {
        
        return (
            <div>
                This is a device view for device id: {this.state.deviceId}
                <NotificationView userId={this.state.userId}/>
                {this.state.dataReady ? <LocationView locations={this.state.locationList}/> : <p>No Location Data</p>}
                
            </div>
          );
    }
}
 
export default withRouter(DeviceView);

