import React, { Component } from 'react';
import { Container, Box, Columns, Notification, Heading } from 'react-bulma-components';

class FitnessWeekSumGraph extends Component {
	render() {
		if (this.props.sumData) {
			return (
				<Box>
					<Container>
						<Heading>{this.props.title}</Heading>
						<Columns>
							<Columns.Column>Total Time</Columns.Column>
							<Columns.Column>{this.props.sumData.totalTime} Minutes</Columns.Column>
							<Columns.Column></Columns.Column>
							<Columns.Column></Columns.Column>
						</Columns>
						<Columns>
							<Columns.Column>Total Calories</Columns.Column>
							<Columns.Column>{this.props.sumData.totalCalories} Calories</Columns.Column>
							<Columns.Column></Columns.Column>
							<Columns.Column></Columns.Column>
						</Columns>
						<Columns>
							<Columns.Column>Total Miles</Columns.Column>
							<Columns.Column>{this.props.sumData.totalMiles} Miles</Columns.Column>
							<Columns.Column></Columns.Column>
							<Columns.Column></Columns.Column>
						</Columns>
					</Container>
					{this.props.children}
				</Box>
			);
		}
		else {
			return (
				<Box>
					<Container>
						<Heading>{this.props.title}</Heading>
						<Notification>
							No data found for the selection.
						</Notification>
					</Container>
					{this.props.children}
				</Box>
			);
		}



	}
}

export default FitnessWeekSumGraph;