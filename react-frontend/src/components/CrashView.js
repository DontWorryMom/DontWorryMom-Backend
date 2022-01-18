import React from 'react';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';
var config = require("../Config").config;

class CrashView extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            crashList: null
        }

        this.index = null;
    }

    componentDidMount() {
        this.get_data()
    }

    get_data() {
        var crashes = new XMLHttpRequest()
        crashes.addEventListener('load', () => {
            // update the state of the component with the result here
            console.log(crashes.responseText);
            const cList = JSON.parse(crashes.responseText).results;
            this.setState({crashList: cList})
        })
        crashes.open('GET', config.SPRING_BACKEND_FULL_URL + `/locations/deviceId/${this.props.deviceId}?onlyCrashes=true`)
        console.log(this.props.deviceId)
        crashes.send()
    }

    // helper function to print each crash
    print_crash(crash) {
        return(
            <ListItem>
                <ListItemText primary={crash.locationTime}/>
            </ListItem>
        )
    }

    render() {
        if (this.state.crashList != null) {
            return(
                <div>CrashView Rendered
                    <List sx={{ width: '100%', maxWidth: 360, bgcolor: 'background.paper' }}>
                            {this.state.crashList.map(this.print_crash)}
                    </List>
                </div>
            )
        }
        else {
            return ( <div>There are no crashes to list </div> )
        }
    }
}

export default (CrashView);