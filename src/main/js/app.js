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
import LoadingIcon from "./loading-icon";


class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {
			loading: true, fitnessWeeks: [], "graphAttrs": [
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

		this.addWeek = this.addWeek.bind(this);
		this.sortWeeks = this.sortWeeks.bind(this);
	}

	componentDidMount() {
		this.setState({ "loading": true });
		fetch('./rest/fitnessWeeks')
			.then(res => res.json())
			.then((data) => {
				this.setState({ fitnessWeeks: data });
				this.sortWeeks();
				this.setState({ "loading": false });
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
						<FitnessWeekForm title="Add Week" addWeek={this.addWeek} />
					</Route>
					<Route path="/">
						<FitnessWeekTable title="All Weeks" weeks={this.state.fitnessWeeks} />
						<LoadingIcon isShown={this.state.loading} />
					</Route>
				</Switch>
			</Router>
		);
	}

	addWeek(createdWeek) {
		this.state.fitnessWeeks.push(createdWeek);
		this.sortWeeks();
		this.setState({ "loading": false });
	}

	sortWeeks() {
		this.state.fitnessWeeks.sort(this.compareWeeks);
	}

	compareWeeks(a, b) {
		if (a.dateRecorded > b.dateRecorded) return 1;
		if (b.dateRecorded > a.dateRecorded) return -1;

		return 0;
	}
}

ReactDOM.render(
	<App />,
	document.getElementById('react')
)