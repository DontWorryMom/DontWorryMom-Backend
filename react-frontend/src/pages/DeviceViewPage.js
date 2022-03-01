// here put all the stuff unique to a specific device
import React from 'react';
import { withRouter } from "react-router";
import Box from '@mui/material/Box';
import Typography from "@mui/material/Typography";

import LocationView from '../components/LocationView';
import NotificationView from '../components/NotificationView';
import CrashView from '../components/CrashView';

import Navbar from '../components/Navbar';

var config = require("../Config").config;

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
          //console.log(locs.responseText);
          const locations = JSON.parse(locs.responseText).results;
          this.setState({locationList: locations})
          this.setState({dataReady: true});
        })

        // added because of login security
        locs.withCredentials=true;
        
        // open the request with the verb and the url
        locs.open('GET', config.SPRING_BACKEND_FULL_URL + `/locations/deviceId/${deviceId}`)        
        // send the request
        locs.send()
    }


    render() {
        
        return (
            <div><Navbar />
            <Box sx={{
                //add styling here
                display: 'flex',
                flexDirection: 'column',
                alignItems: { xs: 'center'},
                
                }}
            >
                <Typography
                    variant="h4"
                    component="div"
                    align='center'
                    sx={{ mr: 2, display: { xs: "none", md: "flex" } }}
                    > 
                        <Box sx={{textAlign:'right'}}>Device {this.state.deviceId} Information</Box>
                    </Typography>
            </Box>
            
            <Box
                sx={{
                    //add styling here
                }}
            >
                <Box sx={{ display: 'grid', gridTemplateColumns: 'repeat(3, 1fr)' }}>
                    <NotificationView userId={this.state.userId}/>
                    {this.state.dataReady ? <LocationView locations={this.state.locationList}/> : <p>No Location Data</p>}
                    <CrashView deviceId={this.state.deviceId}/>
                </Box>
                
                
                
            </Box>
            </div>
          );
    }
}
 
export default withRouter(DeviceView);

