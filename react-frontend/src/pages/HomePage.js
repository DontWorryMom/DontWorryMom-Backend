import React from 'react';
import dont_worry_mom_gif from '../resources/dont-worry-mom.gif';

class HomePage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            // initialize local state here
        };
        // ... other stuff needed for initialization
    }

    render() {
        return (
            <header className="App-header">
                <img src={dont_worry_mom_gif} className="DWM-logo" alt="Don't Worry Mom" />
                <p>
                    Don't Worry Mom
                </p>
            </header>
        );
    }
}

export default (HomePage);