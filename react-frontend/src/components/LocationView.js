import React from 'react';
import GoogleMapReact from 'google-map-react';
import Button from '@mui/material/Button';
import Box from '@mui/material/Box';
var config = require("../Config").config;

class LocationView extends React.Component {
    //props.locations is locations JSON object
    constructor(props) {
        super(props);
        this.state = {
            time: null,
            lat: null,
            long: null,
            initialized: false,
            renderMap: true
          }
        
        this.initialize_state = this.initialize_state.bind(this);
        this.get_next_loc = this.get_next_loc.bind(this);
        this.get_prev_loc = this.get_prev_loc.bind(this);

        this.index = null;
        //this.initialize_state();
    }

    componentDidMount() {
        this.initialize_state();
    }


    initialize_state() {
        if (!(this.state.initialized) && this.props.locations != null && this.props.locations.length > 0)
        {
            this.index = 0;
            this.setState ({
                time: this.props.locations[this.index].locationTime,
                lat: this.props.locations[this.index].locationLat,
                long: this.props.locations[this.index].locationLon,
                initialized: true
            })
            
        }
    }

    get_next_loc() {
        if (this.index < this.props.locations.length - 1) {
            this.index = this.index + 1;
            this.setState ({
                time: this.props.locations[this.index].locationTime,
                lat: this.props.locations[this.index].locationLat,
                long: this.props.locations[this.index].locationLon
            })
        }
    }

    get_prev_loc() {
        if (this.index !== 0) {
            this.index = this.index - 1;
            this.setState ({
                time: this.props.locations[this.index].locationTime,
                lat: this.props.locations[this.index].locationLat,
                long: this.props.locations[this.index].locationLon
            })
        }
    }

    print_location() {
        return (
            <Box sx={{
                //add styling here
                display: 'flex',
                flexDirection: 'column',
                alignItems: { xs: 'center'},
            }}
            >
                <p>{this.index}</p>
                <p>{this.state.time}</p>
                <p>{this.state.lat}</p>
                <p>{this.state.long}</p>
            </Box>
        )
    }

    render() {
        
        const coords = {lat: this.state.lat, lng: this.state.long};

        const renderMarkers = (map, maps) => {
            let marker = new maps.Marker({
            position: { lat: this.state.lat, lng: this.state.long },
            map,
            title: 'Hello World!'
            });
            return marker;
        };

        return (
            <Box sx={{
                //add styling here
                display: 'flex',
                flexDirection: 'column',
                alignItems: { xs: 'center'},
                
                }}
            >
                {this.print_location()}
                <div>
                <Button onClick={this.get_prev_loc} variant="outlined" color="primary" >
					Show Previous Location
				</Button>
				<Button onClick={this.get_next_loc} variant="outlined" color="primary" >
					Show Next Location
				</Button>
                </div>

                <div style={{height: '50vh', width: '100%', display: "flex",justifyContent: "center", alignItems: "center", backgroundColor: "black"}}>
                { this.state.renderMap &&
                <GoogleMapReact
                    bootstrapURLKeys= {{key: config.GOOGLE_MAPS_API_KEY}}
                    defaultCenter={coords}
                    center={coords}
                    defaultZoom={14}
                    margin={[50,50,50,50]}
                    yesIWantToUseGoogleMapApiInternals
                    onGoogleApiLoaded={({ map, maps }) => renderMarkers(map, maps)}
                >

                </GoogleMapReact>
                }
                </div>
                

            </Box>
        );
    }
}

export default (LocationView);