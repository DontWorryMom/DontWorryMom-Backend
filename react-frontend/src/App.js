
// used for page navigation
import { 
  BrowserRouter as Router, 
  Switch, 
  Route,
  Link 
} from 'react-router-dom';

// used to set website title on tab
import { Helmet } from "react-helmet";
import './App.css';

// pages created
import HomePage from './pages/HomePage';
import UserSelectionPage from './pages/UserSelectionPage';
import DeviceSelectionPage from './pages/DeviceSelectionPage';

function App() {
  return (
    <Router>
    <div className="App">
      <Helmet>
          <title>Don't Worry Mom</title>
      </Helmet>

      <div>
        <Link to='/'>Home</Link>
        <br/>
        <Link to='/users'>User List</Link>
      </div>
      
      <Switch>
        <Route exact path='/' component={HomePage}></Route>
        <Route exact path='/users' component={UserSelectionPage}></Route>
        <Route exact path='/userID/devices' component={DeviceSelectionPage}></Route>
      </Switch>
      
    </div>
    </Router>
  );
}

export default App;