import React, { Component } from 'react';
import { Container, Form, Button } from 'react-bulma-components';
import Moment from 'moment';

class FitnessWeekDateFilter extends Component {
	constructor(props) {
		super(props);
		this.state = {
			"fromDate": this.props.startDate ? this.props.startDate : '',
			"toDate": this.props.endDate ? this.props.endDate : '',
			"hasUserModified": false,
			"errors": []
		};
		this.handleChangeDate = this.handleChangeDate.bind(this);
		this.handleClickFilterButton = this.handleClickFilterButton.bind(this);
		this.validateDates = this.validateDates.bind(this);
	}

	validateDates(startDate, endDate) {
		const today = Moment();
		const startAsMomentDate = Moment(startDate, "YYYY-MM-DD");
		const endAsMomentDate = Moment(endDate, "YYYY-MM-DD");
		const validationErrors = [];
		if (startDate == '') {
			validationErrors.push("From date must not be empty.");
		}
		else if (!startAsMomentDate.isValid()) {
			validationErrors.push("From date must be a date of the format 2020-12-31.");
		}
		else if (startAsMomentDate.isAfter(today)) {
			validationErrors.push("From date must not be in the future.");
		}

		if (endDate == '') {
			validationErrors.push("To date must not be empty.");
		}
		else if (!endAsMomentDate.isValid()) {
			validationErrors.push("To date must be a date of the format 2020-12-31.");
		}
		else if (endAsMomentDate.isAfter(today)) {
			validationErrors.push("To date must not be in the future.");
		}
		else if (startAsMomentDate.isValid() && startAsMomentDate.isAfter(endAsMomentDate)) {
			validationErrors.push("From date cannot occur after To date.");
		}
		else if (startAsMomentDate.isValid() && startAsMomentDate.isSame(endAsMomentDate)) {
			validationErrors.push("From date cannot be the same as To date.");
		}

		return validationErrors;
	}

	handleChangeDate(event) {
		event.preventDefault();
		const name = event.target.name;
		const value = event.target.value;
		if (name === "fromDate" || name === "toDate") {
			this.setState({ [name]: value, "hasUserModified": true });
		}
		else {
			console.log("handleChangeDate called for wrong input: " + name + " with value: " + value);
		}
	}

	handleClickFilterButton(event) {
		event.preventDefault();
		const validationErrors = this.validateDates(this.state.fromDate, this.state.toDate);
		if (validationErrors && validationErrors.length > 0) {
			this.setState({ "errors": validationErrors });
		}
		else {
			this.setState({ "errors": [] });
			this.props.onFilterDates(this.state.fromDate, this.state.toDate);
		}
	}

	componentWillReceiveProps(nextProps) {
		//update the dates selected when the props change
		//do not change the state if the dates are already selected from user input
		if (!this.state.hasUserModified && nextProps.startDate !== this.state.fromDate) {
			this.setState({ "fromDate": nextProps.startDate });
		}

		if (!this.state.hasUserModified && nextProps.endDate !== this.state.toDate) {
			this.setState({ "toDate": nextProps.endDate });
		}

	}

	render() {
		const errors = this.state.errors.map((error, index) => (<Form.Help key={index} color="danger">{error}</Form.Help>));
		return (
			<Container className="spacedContainer">
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
				<Form.Field>
					{errors}
				</Form.Field>
			</Container>
		);
	}
}

export default FitnessWeekDateFilter;
