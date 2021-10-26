import React from 'react';

class UserSelectionPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            // initialize local state here
            users: []
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
          const results = JSON.parse(req.responseText).results;
          this.setState({users: results})
        })
        // open the request with the verb and the url
        req.open('GET', 'http://localhost:8080/users/')
        // send the request
        req.send()
    }
    
    render() {
        const userLength = this.state.users.length;
        console.log(this.state.users);
        console.log('type of users is ' + typeof this.state.users);

        return (
            <div>
                <div>There are currently {userLength} users</div>
                <div>
                    {JSON.stringify(this.state.users)}
                </div>
                <div>
                    <ul>
                        {this.state.users.map(user => {
                            return <li key={user}>{JSON.stringify(user[0])}</li>;
                        })}
                    </ul>
                </div>
            </div>
        );
        // ... code to render page
    }
}

export default (UserSelectionPage);