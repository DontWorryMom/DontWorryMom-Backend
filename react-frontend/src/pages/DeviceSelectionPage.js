import React from 'react';

class DeviceSelectionPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            // initialize local state here
        };
        // ... other stuff needed for initialization
    }

    render() {
        return (
            <div>
                {/*Hardcoded this temporarily*/}
                <div>'Device 1'</div>
                <div>'Device 2'</div>
            </div>
        );
        // ... code to render page
    }
}

export default (DeviceSelectionPage);