import './App.css';
import './uikit.min.css';
import 'rc-slider/assets/index.css';
import './App.css';
import {
  BrowserRouter as Router,
  Switch,
  Route
} from "react-router-dom";
import NoMatchPage from "./404/NoMatchPage";
import SearchPage from "./search/SearchPage";

function App() {
  return (
    <div className="limited-container">
      <Router>
        <Switch>
          <Route exact path="/">
            <SearchPage/>
          </Route>
          <Route path="*">
            <NoMatchPage />
          </Route>
        </Switch>
      </Router>
    </div>
  );
}

export default App;
