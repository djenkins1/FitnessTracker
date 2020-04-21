import React, { Component } from 'react';
import { XYPlot, LineSeries, XAxis, YAxis, HorizontalGridLines, VerticalGridLines } from 'react-vis';
import { Container, Notification } from 'react-bulma-components';
import FitnessWeekSumReport from './FitnessWeekSumReport';

class FitnessWeekGraph extends Component {
	render() {
		if (this.props.weeks && this.props.weeks.length > 1) {
			const data = this.convertWeeksToData(this.props.weeks, this.props.showByX, this.props.showByY);
			//find the largest value and add 25 percent to it for better visibility
			const attr = this.props.showByY;
			var largestValue = Math.max.apply(Math, this.props.weeks.map(function (o) { return o[attr]; }));
			const chartDomain = [0, largestValue * 1.25];
			//if there is only one element in the data then the graph was not displaying properly
			//set ticks as the workaround
			return (
				<Container>
					<XYPlot
						margin={{ left: 100, bottom: 100 }}
						xType="time"
						width={this.props.chartWidth}
						height={this.props.chartHeight}
						yDomain={chartDomain}
					>
						<XAxis tickLabelAngle={-90} tickTotal={data.length} />
						<YAxis />
						<HorizontalGridLines />
						<VerticalGridLines />
						<LineSeries data={data} />
					</XYPlot>
				</Container>
			);
		}
		else if (this.props.weeks && this.props.weeks.length == 1) {
			//show a sum report if there is only one week in the data set
			return (
				<Container>
					<FitnessWeekSumReport title="" sumData={this.props.weeks[0]} />
				</Container>
			);
		}
		else {
			return (
				<Container>
					<Notification>
						No data found for the selection.
					</Notification>
				</Container>
			);
		}

	}

	convertWeeksToData(weeks, xAttr, yAttr) {
		var toReturn = [];
		var newWeekPoint;

		for (var i = 0; i < weeks.length; i++) {
			newWeekPoint = {};
			newWeekPoint.x = new Date(weeks[i][xAttr]);
			newWeekPoint.y = weeks[i][yAttr];
			newWeekPoint.label = weeks[i][yAttr].toString();
			toReturn.push(newWeekPoint);
		}

		return toReturn;
	}
}

export default FitnessWeekGraph