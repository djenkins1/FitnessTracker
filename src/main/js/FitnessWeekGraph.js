import React, { Component } from 'react';
import { XYPlot, LineSeries, XAxis, YAxis, HorizontalGridLines, VerticalGridLines } from 'react-vis';
import { Container, Notification } from 'react-bulma-components';
import Moment from 'moment';

class FitnessWeekGraph extends Component {
	render() {
		if (this.props.weeks && this.props.weeks.length > 0) {
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
						margin={{ left: 50, bottom: 100 }}
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

		//add a starting point if only one week
		if (weeks.length == 1) {
			newWeekPoint = {};
			newWeekPoint.x = Moment(new Date(weeks[0][xAttr])).subtract(1, "days").toDate();
			newWeekPoint.y = 0;
			toReturn.push(newWeekPoint);
		}

		for (var i = 0; i < weeks.length; i++) {
			newWeekPoint = {};
			newWeekPoint.x = new Date(weeks[i][xAttr]);
			newWeekPoint.y = weeks[i][yAttr];
			toReturn.push(newWeekPoint);
		}

		//add an ending point if only one week
		if (weeks.length == 1) {
			newWeekPoint = {};
			newWeekPoint.x = Moment(new Date(weeks[0][xAttr])).add(1, "days").toDate();
			newWeekPoint.y = 0;
			toReturn.push(newWeekPoint);
		}

		return toReturn;
	}
}

export default FitnessWeekGraph