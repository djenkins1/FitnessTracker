import React, { Component } from 'react';
import { Box, Heading, Form, Button } from 'react-bulma-components';


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
			"exerciseType": ""
		};

		this.handleInputChange = this.handleInputChange.bind(this);
	}

	render() {
		return (
			<Box>
				<Heading>{this.props.title}</Heading>
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
						<Button color="danger">Cancel</Button>
					</Form.Control>
				</Form.Field>
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
}


export default FitnessWeekForm;
