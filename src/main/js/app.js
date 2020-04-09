const React = require('react');
const ReactDOM = require('react-dom');
import { Navbar } from "react-bulma-components";
import {
	BrowserRouter as Router,
	Switch,
	Route,
	Link
} from "react-router-dom";

import FitnessWeekTable from './fitness-week-table';
import FitnessWeekGraphFilter from './fitness-week-graph-filter';
import FitnessWeekForm from "./fitness-week-form";


class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {
			fitnessWeeks: [], "graphAttrs": [
				{
					"id": 0,
					"title": "Total Miles",
					"attr": "totalMiles"
				},
				{
					"id": 1,
					"title": "Total Calories",
					"attr": "totalCalories"
				},
				{
					"id": 2,
					"title": "Total Time",
					"attr": "totalTime"
				}
			]
		};
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
					<Link to="/create" className={"navbar-item has-background-primary"}>Add Week</Link>
				</Navbar>
				<Switch>
					<Route path="/graph">
						<FitnessWeekGraphFilter showAttrs={this.state.graphAttrs} weeks={this.state.fitnessWeeks} />
					</Route>
					<Route path="/create">
						<FitnessWeekForm title="Add Week" />
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