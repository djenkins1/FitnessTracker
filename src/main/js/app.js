const React = require('react');
const ReactDOM = require('react-dom');
import { Navbar } from "react-bulma-components";
import Moment from 'moment';
import {
	BrowserRouter as Router,
	Switch,
	Route,
	Link
} from "react-router-dom";

import FitnessWeekTable from './FitnessWeekTable';
import FitnessWeekGraphFilter from './FitnessWeekGraphFilter';
import FitnessWeekForm from "./FitnessWeekForm";
import FitnessWeekDateFilter from "./FitnessWeekDateFilter";
import FitnessWeekSumReport from "./FitnessWeekSumReport";


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
			], "sumForWeeks": {}, "fitnessWeekSums": []
		};

		this.addWeek = this.addWeek.bind(this);
		this.sortWeeks = this.sortWeeks.bind(this);
		this.handleFilterDates = this.handleFilterDates.bind(this);
		this.getDateFilterDetails = this.getDateFilterDetails.bind(this);
		this.getSumDataForDates = this.getSumDataForDates.bind(this);
		this.getSumsByMonths = this.getSumsByMonths.bind(this);
	}

	componentDidMount() {
		const defaultDates = this.getDefaultDatesForSum();
		this.setState({ "loading": true });
		fetch('./rest/fitnessWeeks')
			.then(res => res.json())
			.then((data) => {
				this.setState({ fitnessWeeks: data });
				this.sortWeeks();
				this.getSumDataForDates(defaultDates.startDate, defaultDates.endDate);
				this.getSumsByMonths(defaultDates.startDate, defaultDates.endDate);
			})
			.catch(console.log);

	}

	getDefaultDatesForSum() {
		const JANUARY = 0;//months start at 0
		Moment.locale('en');//TODO: pull this out to some configuration?
		const startOfYear = Moment(new Date(new Date().getFullYear(), JANUARY, 1)).format('YYYY-MM-DD');
		const today = Moment(new Date()).format('YYYY-MM-DD');
		return {
			"startDate": startOfYear,
			"endDate": today
		};
	}

	getDateFilterDetails() {
		//this grabs the first and last fitness weeks to use as the default start and end dates
		if (this.state.fitnessWeeks && this.state.fitnessWeeks.length > 0) {
			const firstIndex = 0;
			const lastIndex = this.state.fitnessWeeks.length - 1;
			const startDate = this.state.fitnessWeeks[firstIndex].dateRecorded;
			const endDate = this.state.fitnessWeeks[lastIndex].dateRecorded;
			return {
				"startDate": startDate,
				"endDate": endDate
			};
		}
		else {
			return {};
		}

	}

	render() {
		const dateFilter = this.getDateFilterDetails();
		const defaultDatesForSum = this.getDefaultDatesForSum();
		return (
			<Router>
				<Navbar color="info">
					<Navbar.Brand>
						<Navbar.Item renderAs="span"><i className="fas fa-running"></i> Fitness Tracker</Navbar.Item>
						<Navbar.Burger data-target="navLinksMenu" />
					</Navbar.Brand>
					<Navbar.Menu className="is-active">
						<Navbar.Container>
							<Link className="navbar-item" to="/" >Home</Link>
							<Link className="navbar-item" to="/graph" >Daily Graph</Link>
							<Link className="navbar-item" to="/sums">Monthly Graph</Link>
							<Link className="navbar-item" to="/sumsAnnual" >Annual Report</Link>
							<Link className="navbar-item" to="/create" >Add Week</Link>
						</Navbar.Container>
					</Navbar.Menu>
				</Navbar>
				<Switch>
					<Route path="/graph">
						<FitnessWeekGraphFilter showByX="dateRecorded" showAttrs={this.state.graphAttrs} weeks={this.state.fitnessWeeks}>
							<FitnessWeekDateFilter {...dateFilter} onFilterDates={this.handleFilterDates} />
						</FitnessWeekGraphFilter>
					</Route>
					<Route path="/create">
						<FitnessWeekForm title="Add Week" addWeek={this.addWeek} />
					</Route>
					<Route path="/sumsAnnual">
						<FitnessWeekSumReport title="Annual Report" sumData={this.state.sumForWeeks}>
							<FitnessWeekDateFilter {...defaultDatesForSum} onFilterDates={this.getSumDataForDates} />
						</FitnessWeekSumReport>
					</Route>
					<Route path="/sums">
						<FitnessWeekGraphFilter showByX="endDate" showAttrs={this.state.graphAttrs} weeks={this.state.fitnessWeekSums}>
							<FitnessWeekDateFilter {...defaultDatesForSum} onFilterDates={this.getSumsByMonths} />
						</FitnessWeekGraphFilter>
					</Route>
					<Route path="/">
						<FitnessWeekTable title="All Weeks" weeks={this.state.fitnessWeeks} />
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

	getSumDataForDates(startDate, endDate) {
		this.setState({ "loading": true });
		const url = './rest/fitnessWeeks/sum/between?startDate=' + startDate + '&endDate=' + endDate;
		//TODO: catch 404 error by checking response.status in .then
		fetch(url, {
			method: 'GET',
			mode: 'cors',
			cache: 'no-cache',
			credentials: 'same-origin',
			headers: {
				'Content-Type': 'application/json'
			},
			redirect: 'error',
			referrerPolicy: 'no-referrer'
		})
			.then(res => res.json())
			.then((data) => {
				this.setState({ sumForWeeks: data });
				this.setState({ "loading": false });
			})
			.catch(console.log);
	}

	handleFilterDates(startDate, endDate) {
		this.setState({ "loading": true });
		const url = './rest/fitnessWeeks/between?startDate=' + startDate + '&endDate=' + endDate;
		fetch(url, {
			method: 'GET',
			mode: 'cors',
			cache: 'no-cache',
			credentials: 'same-origin',
			headers: {
				'Content-Type': 'application/json'
			},
			redirect: 'error',
			referrerPolicy: 'no-referrer'
		})
			.then(res => res.json())
			.then((data) => {
				this.setState({ fitnessWeeks: data });
				this.sortWeeks();
				this.setState({ "loading": false });
			})
			.catch(console.log);
	}

	getSumsByMonths(startDate, endDate) {
		this.setState({ "loading": true });
		const url = './rest/fitnessWeeks/sums?startDate=' + startDate + '&endDate=' + endDate;
		//TODO: catch 404 error by checking response.status in .then
		fetch(url, {
			method: 'GET',
			mode: 'cors',
			cache: 'no-cache',
			credentials: 'same-origin',
			headers: {
				'Content-Type': 'application/json'
			},
			redirect: 'error',
			referrerPolicy: 'no-referrer'
		})
			.then(res => res.json())
			.then((data) => {
				this.setState({ fitnessWeekSums: data });
				this.setState({ "loading": false });
			})
			.catch(console.log);
	}
}

ReactDOM.render(
	<App />,
	document.getElementById('react')
)