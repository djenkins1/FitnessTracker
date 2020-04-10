import React, { Component } from 'react';
import { Box, Heading, Form, Button } from 'react-bulma-components';
import { Redirect } from 'react-router-dom';
import LoadingIcon from './loading-icon';


class FitnessWeekForm extends Component {
	constructor(props) {
		super(props);
		this.state = {
			"totalTime": 0,
			"totalMiles": 0,
			"totalCalories": 0,
			"milesToDate": 0,
			"daysExercised": "",
			"dateRecorded": "",
			"exerciseType": "",
			"redirect": false
		};

		this.handleInputChange = this.handleInputChange.bind(this);
		this.handleSubmit = this.handleSubmit.bind(this);
		this.handleCancelClick = this.handleCancelClick.bind(this);
	}

	render() {
		if (this.state.redirect) {
			return (
				<Redirect to="/" />
			);
		}

		if (this.state.loading) {
			return (
				<LoadingIcon isShown={this.state.loading} />
			);
		}
		return (
			<Box>
				<Heading>{this.props.title}</Heading>
				<form onSubmit={this.handleSubmit}>
					<Form.Field>
						<Form.Label>Total Time(Minutes)</Form.Label>
						<Form.Control>
							<Form.Input type="number" name="totalTime" placeholder="120" value={this.state.totalTime} onChange={this.handleInputChange} />
						</Form.Control>
					</Form.Field>
					<Form.Field>
						<Form.Label>Total Miles</Form.Label>
						<Form.Control>
							<Form.Input type="number" name="totalMiles" placeholder="20.5" value={this.state.totalMiles} onChange={this.handleInputChange} />
						</Form.Control>
					</Form.Field>
					<Form.Field>
						<Form.Label>Total Calories</Form.Label>
						<Form.Control>
							<Form.Input type="number" name="totalCalories" placeholder="250.25" value={this.state.totalCalories} onChange={this.handleInputChange} />
						</Form.Control>
					</Form.Field>
					<Form.Field>
						<Form.Label>Miles to Date</Form.Label>
						<Form.Control>
							<Form.Input type="number" name="milesToDate" placeholder="1250" value={this.state.milesToDate} onChange={this.handleInputChange} />
						</Form.Control>
					</Form.Field>
					<Form.Field>
						<Form.Label>Days Exercised</Form.Label>
						<Form.Control>
							<Form.Input type="text" name="daysExercised" placeholder="M/T/W" value={this.state.daysExercised} onChange={this.handleInputChange} />
						</Form.Control>
					</Form.Field>
					<Form.Field>
						<Form.Label>Exercise Type</Form.Label>
						<Form.Control>
							<Form.Input type="text" name="exerciseType" placeholder="Cycling" value={this.state.exerciseType} onChange={this.handleInputChange} />
						</Form.Control>
					</Form.Field>
					<Form.Field>
						<Form.Label>Date Recorded</Form.Label>
						<Form.Control>
							<Form.Input type="date" name="dateRecorded" placeholder="2019-12-25" value={this.state.dateRecorded} onChange={this.handleInputChange} />
						</Form.Control>
					</Form.Field>
					<Form.Field kind="group">
						<Form.Control>
							<Button color="primary">Submit</Button>
						</Form.Control>
						<Form.Control>
							<Button onClick={this.handleCancelClick} color="danger">Cancel</Button>
						</Form.Control>
					</Form.Field>
				</form>
			</Box>
		);
	}

	handleInputChange(event) {

		const target = event.target;
		const value = target.value;
		const name = target.name;
		this.setState({
			[name]: value
		});
	}

	handleSubmit(event) {
		event.preventDefault();
		this.createWeek(event, this.state);
	}

	createWeek(event, formData) {
		this.setState({ "loading": true });
		const url = "./rest/fitnessWeeks";
		fetch(url, {
			method: 'POST',
			mode: 'cors',
			cache: 'no-cache',
			credentials: 'same-origin',
			headers: {
				'Content-Type': 'application/json'
			},
			redirect: 'error',
			referrerPolicy: 'no-referrer',
			body: JSON.stringify(formData)
		}).then(res => res.json())
			.then((createdWeek) => {
				this.props.addWeek(createdWeek);
				this.setState({ "redirect": true });
			})
			.catch(console.log);
	}

	handleCancelClick(event) {
		event.preventDefault();
		this.setState({ "redirect": true });
	}
}


export default FitnessWeekForm;
