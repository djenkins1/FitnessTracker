import React, { Component } from 'react';
import { Container, Form, Button } from 'react-bulma-components';

class FitnessWeekDateFilter extends Component {
	constructor(props) {
		super(props);
		this.state = {
			"fromDate": this.props.startDate ? this.props.startDate : '',
			"toDate": this.props.endDate ? this.props.endDate : '',
			"hasUserModified" : false
		};
		this.handleChangeDate = this.handleChangeDate.bind(this);
		this.handleClickFilterButton = this.handleClickFilterButton.bind(this);
	}

	handleChangeDate(event) {
		event.preventDefault();
		const name = event.target.name;
		const value = event.target.value;
		if (name === "fromDate" || name === "toDate") {
			this.setState({ [name]: value, "hasUserModified" : true });
		}
		else {
			console.log("handleChangeDate called for wrong input: " + name + " with value: " + value);
		}
	}

	handleClickFilterButton(event) {
		event.preventDefault();
		if (this.state.fromDate && this.state.toDate) {
			this.props.onFilterDates(this.state.fromDate, this.state.toDate);
		}
	}

	componentWillReceiveProps(nextProps) {
		//update the dates selected when the props change
		//do not change the state if the dates are already selected from user input
		if ( !this.state.hasUserModified && nextProps.startDate !== this.state.fromDate) {
			console.log("start date " + nextProps.startDate);
			this.setState({ "fromDate": nextProps.startDate });
		}

		if ( !this.state.hasUserModified && nextProps.endDate !== this.state.toDate) {
			console.log("end date " + nextProps.endDate);
			this.setState({ "toDate": nextProps.endDate });
		}

	}

	render() {
		return (
			<Container>
				<Form.Field kind="group">
					<Form.Control>
						<Form.Label>From </Form.Label>
					</Form.Control>
					<Form.Control>
						<Form.Input type="date" name="fromDate" value={this.state.fromDate} onChange={this.handleChangeDate} />
					</Form.Control>
					<Form.Control>
						<Form.Label>To </Form.Label>
					</Form.Control>
					<Form.Control>
						<Form.Input type="date" name="toDate" value={this.state.toDate} onChange={this.handleChangeDate} />
					</Form.Control>
					<Form.Control>
						<Button color="info" onClick={this.handleClickFilterButton}>Filter</Button>
					</Form.Control>
				</Form.Field>
			</Container>
		);
	}
}

export default FitnessWeekDateFilter;
