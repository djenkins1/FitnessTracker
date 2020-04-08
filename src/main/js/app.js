const React = require('react');
const ReactDOM = require('react-dom');
import FitnessWeekList from './fitness-week-list';
import {XYPlot, LineSeries} from 'react-vis';
import GraphDataMock from './graphmock'
import FitnessWeekGraph from './fitness-week-graph';
import {
	  BrowserRouter as Router,
	  Switch,
	  Route,
	  Link
	} from "react-router-dom";

class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {fitnessWeeks: []};
	}

	componentDidMount() {
        fetch('./rest/fitnessWeeks')
        .then(res => res.json())
        .then((data) => {
          this.setState({ fitnessWeeks: data })
        })
        .catch(console.log)
	}

	render() {
		return (
				 <Router>
			      <div>
			        <nav>
			          <ul>
			            <li>
			              <Link to="/">Home</Link>
			            </li>
			            <li>
			              <Link to="/graph">Graph</Link>
			            </li>
			          </ul>
			        </nav>
			        <Switch>
			          <Route path="/graph">
			          	<FitnessWeekGraph weeks={this.state.fitnessWeeks}/>	
			          </Route>
			          <Route path="/">
			          	<FitnessWeekList weeks={this.state.fitnessWeeks}/>
			          </Route>
			        </Switch>
			      </div>
			    </Router>
		)
	}
}

ReactDOM.render(
		<App />,
		document.getElementById('react')
	)