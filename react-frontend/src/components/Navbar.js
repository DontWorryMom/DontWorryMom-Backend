import React from 'react';
import {Link} from 'react-router-dom';

class Navbar extends React.Component {
    constructor(props) {
        super(props);
        this.state = {  }
    }
    render() { 
        return (
            <div>
                <Link to='/'>Home</Link>
                <br/>
                <Link to='/users'>User List</Link>
            </div>
          );
    }
}
 
export default Navbar;