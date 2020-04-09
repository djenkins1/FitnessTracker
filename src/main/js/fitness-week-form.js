import React, { Component } from 'react';
import { Box, Heading, Form, Button } from 'react-bulma-components';


class FitnessWeekForm extends Component {
	constructor(props) {
		super(props);
		this.state = {
			"formData": {
				"totalTime": 0,
				"totalMiles": 0,
				"totalCalories": 0,
				"milesToDate": 0,
				"daysExercised": "",
				"dateRecorded": "",
				"exerciseType": ""
			}
		};
	}

	render() {
		return (
			<Box>
				<Heading>{this.props.title}</Heading>
				<Form.Field>
					<Form.Label>Total Time(Minutes)</Form.Label>
					<Form.Control>
						<Form.Input type="number" placeholder="120" value={this.state.formData.totalTime} />
					</Form.Control>
				</Form.Field>
				<Form.Field>
					<Form.Label>Total Miles</Form.Label>
					<Form.Control>
						<Form.Input type="number" placeholder="20.5" value={this.state.formData.totalMiles} />
					</Form.Control>
				</Form.Field>
				<Form.Field>
					<Form.Label>Total Calories</Form.Label>
					<Form.Control>
						<Form.Input type="number" placeholder="250.25" value={this.state.formData.totalCalories} />
					</Form.Control>
				</Form.Field>
				<Form.Field>
					<Form.Label>Miles to Date</Form.Label>
					<Form.Control>
						<Form.Input type="number" placeholder="1250" value={this.state.formData.milesToDate} />
					</Form.Control>
				</Form.Field>
				<Form.Field>
					<Form.Label>Days Exercised</Form.Label>
					<Form.Control>
						<Form.Input type="text" placeholder="M/T/W" value={this.state.formData.daysExercised} />
					</Form.Control>
				</Form.Field>
				<Form.Field>
					<Form.Label>Exercise Type</Form.Label>
					<Form.Control>
						<Form.Input type="text" placeholder="Cycling" value={this.state.formData.exerciseType} />
					</Form.Control>
				</Form.Field>
				<Form.Field>
					<Form.Label>Date Recorded</Form.Label>
					<Form.Control>
						<Form.Input type="date" placeholder="2019-12-25" value={this.state.formData.dateRecorded} />
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
}


export default FitnessWeekForm;
