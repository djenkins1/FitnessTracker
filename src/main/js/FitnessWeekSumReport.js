import React, { Component } from 'react';
import { Container, Box, Notification, Heading, Table } from 'react-bulma-components';
import FitnessWeekCompareSumReport from './FitnessWeekCompareSumReport';

class FitnessWeekSumReport extends Component {
	render() {
		if (this.props.sumData && this.props.sumData.totalCalories !== undefined) {
			const timeInDaysHoursMinutes = this.convertMinutes(this.props.sumData.totalTime);
			return (
				<Box>
					<Container>
						<Heading>{this.props.title}</Heading>
						<Table>
							<tbody>
								<tr>
									<td><b>Total Time</b></td>
									<td>{timeInDaysHoursMinutes}</td>
									<FitnessWeekCompareSumReport thisYearValue={this.props.sumData.totalTime}
										lastYearValue={this.props.sumDataLastYear.totalTime} />
								</tr>
								<tr>
									<td><b>Total Calories</b></td>
									<td>{this.props.sumData.totalCalories.toLocaleString()} Calories</td>
									<FitnessWeekCompareSumReport thisYearValue={this.props.sumData.totalCalories}
										lastYearValue={this.props.sumDataLastYear.totalCalories} />
								</tr>
								<tr>
									<td><b>Total Miles</b></td>
									<td>{this.props.sumData.totalMiles.toLocaleString()} Miles</td>
									<FitnessWeekCompareSumReport thisYearValue={this.props.sumData.totalMiles}
										lastYearValue={this.props.sumDataLastYear.totalMiles} />
								</tr>
							</tbody>
						</Table>
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

	convertMinutes(totalMinutes) {
		//minutes to hour (and days) converter
		const MINUTES_PER_HOUR = 60;
		const HOURS_PER_DAY = 24;
		const MINUTES_PER_DAY = MINUTES_PER_HOUR * HOURS_PER_DAY;
		let d = Math.floor(totalMinutes / MINUTES_PER_DAY);
		let h = Math.floor((totalMinutes - (d * MINUTES_PER_DAY)) / MINUTES_PER_HOUR);
		let m = Math.round(totalMinutes % MINUTES_PER_HOUR);
		let dayUnitStr = (d == 1 ? " Day " : " Days ");
		let hourUnitStr = (h == 1 ? " Hour " : " Hours ");
		let minUnitStr = (m == 1 ? " Minute" : " Minutes");

		return (d + dayUnitStr + h + hourUnitStr + m + minUnitStr);
	}
}

export default FitnessWeekSumReport;