import React, { Component } from 'react';
import { Container, Notification, Heading, Table } from 'react-bulma-components';
import FitnessWeekCompareSumReport from './FitnessWeekCompareSumReport';
import TimeUtil from './time-util';

class FitnessWeekSumReport extends Component {
	render() {
		if (this.props.sumData && this.props.sumData.totalCalories !== undefined) {
			const timeInDaysHoursMinutes = TimeUtil.convertMinutes(this.props.sumData.totalTime);
			return (
				<Container>
					<Container>
						<Heading>{this.props.title}</Heading>
						<Table>
							<tbody>
								<tr>
									<td><b>Total Time</b></td>
									<td>{timeInDaysHoursMinutes}</td>
									<FitnessWeekCompareSumReport thisYearValue={this.props.sumData.totalTime}
										lastYearValue={this.props.sumDataLastYear.totalTime} displayType="time" />
								</tr>
								<tr>
									<td><b>Total Calories</b></td>
									<td>{this.props.sumData.totalCalories.toLocaleString()} Calories</td>
									<FitnessWeekCompareSumReport thisYearValue={this.props.sumData.totalCalories}
										lastYearValue={this.props.sumDataLastYear.totalCalories} displayType="decimal" />
								</tr>
								<tr>
									<td><b>Total Miles</b></td>
									<td>{this.props.sumData.totalMiles.toLocaleString()} Miles</td>
									<FitnessWeekCompareSumReport thisYearValue={this.props.sumData.totalMiles}
										lastYearValue={this.props.sumDataLastYear.totalMiles} displayType="decimal" />
								</tr>
							</tbody>
						</Table>
					</Container>
					{this.props.children}
				</Container>
			);
		}
		else {
			return (
				<Container>
					<Container>
						<Heading>{this.props.title}</Heading>
						<Notification>
							No data found for the selection.
						</Notification>
					</Container>
					{this.props.children}
				</Container>
			);
		}
	}
}

export default FitnessWeekSumReport;