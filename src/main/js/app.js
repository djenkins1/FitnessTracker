const React = require('react');
const ReactDOM = require('react-dom');
import FitnessWeekTable from './fitness-week-table';
import FitnessWeekGraph from './fitness-week-graph';
import { Navbar, NavbarItem } from "react-bulma-components";
import {
	BrowserRouter as Router,
	Switch,
	Route,
	Link
} from "react-router-dom";

class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = { fitnessWeeks: [] };
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
				<Navbar>
					<Link to="/" className={"navbar-item has-background-primary"}>Home</Link>
					<Link to="/graph" className={"navbar-item has-background-primary"}>Graph</Link>
				</Navbar>
				<Switch>
					<Route path="/graph">
						<FitnessWeekGraph showBy="totalMiles" title="Total Miles" weeks={this.state.fitnessWeeks} />
					</Route>
					<Route path="/">
						<FitnessWeekTable title="All Weeks" weeks={this.state.fitnessWeeks} />
					</Route>
				</Switch>
			</Router>
		)
	}
}

ReactDOM.render(
	<App />,
	document.getElementById('react')
)