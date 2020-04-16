import React, { Component } from 'react';
import { XYPlot, LineSeries, LabelSeries, XAxis, YAxis } from 'react-vis';
import { Box, Heading, Container, Notification } from 'react-bulma-components';

class FitnessWeekGraph extends Component {
	render() {
		if (this.props.weeks && this.props.weeks.length > 0) {
			const data = this.convertWeeksToData(this.props.weeks, this.props.showByX, this.props.showByY );
			//find the largest value and add 25 percent to it for better visibility
			const attr = this.props.showByY;
			var largestValue = Math.max.apply(Math, this.props.weeks.map(function (o) { return o[attr]; }));
			const chartDomain = [0, largestValue * 1.25];
			return (
				<Container>
					<XYPlot
						xType="time"
						width={this.props.chartWidth}
						height={this.props.chartHeight}
						yDomain={chartDomain}
					>
						<XAxis />
						<YAxis />
						<LineSeries data={data} />
					</XYPlot>
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

	convertWeeksToData(weeks, xAttr, yAttr ) {
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