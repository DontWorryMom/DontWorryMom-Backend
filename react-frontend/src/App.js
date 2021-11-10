
// used for page navigation
import { 
  BrowserRouter as Router, 
  Switch, 
  Route 
} from 'react-router-dom';

// used to set website title on tab
import { Helmet } from "react-helmet";

// pages created
import HomePage from './pages/HomePage';
import UserSelectionPage from './pages/UserSelectionPage';
import DeviceSelectionPage from './pages/DeviceSelectionPage';
import DeviceView from './components/DeviceView';

// components
import Navbar from './components/Navbar';

function App() {
  return (
    <Router>
    <div className="App">
      <Helmet>
          <title>Don't Worry Mom</title>
      </Helmet>

      <Navbar />
      
      <Switch>
        <Route exact path='/'> <HomePage /> </Route>
        <Route exact path='/users'> <UserSelectionPage /> </Route>
        <Route exact path='/devices/user/:userID'> <DeviceSelectionPage /> </Route>
        <Route exact path='/devices/user/:userID/:deviceID'> <DeviceView /> </Route>
      </Switch>
      
    </div>
    </Router>
  );
}

export default App;