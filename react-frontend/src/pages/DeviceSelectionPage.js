import React from 'react';
import { withRouter } from "react-router";
import { Link } from "react-router-dom";
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import DirectionsBikeIcon from '@mui/icons-material/DirectionsBike';
var config = require("../Config").config;

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

        req.withCredentials=true;
        // open the request with the verb and the url
        req.open('GET', config.SPRING_BACKEND_FULL_URL + `/devices/userId/${id}`)
        // send the request
        req.send()
    }
    
    render() {
        const deviceLength = this.state.devices.length;

        return (
            <div>Num of Devices is {deviceLength}
            <Box
                sx={{
                    //add styling here
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: { xs: 'center'},
                }}
            >
                
                <Box sx={{ flexGrow: 1 }}>
                    <Grid container spacing={6}>
                        {this.state.devices.map((device) => 
                            <Grid item xs={4} sx={{
                                                    //add styling here
                                                    display: 'flex',
                                                    flexDirection: 'column',
                                                    alignItems: { xs: 'center'},
                                                }}>
                                <DirectionsBikeIcon />
                                <br />
                                <Link to={`${this.state.userId}/${device.deviceId}`}>Info for device {device.deviceId}</Link>
                            </Grid>
                        
                        )} 
                        
                    </Grid>
                </Box>
            </Box>
            </div>
        );
        // ... code to render page
    }
}

export default withRouter(DeviceSelectionPage);