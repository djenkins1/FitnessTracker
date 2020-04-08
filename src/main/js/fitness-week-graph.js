import React, { Component } from 'react';
import { XYPlot, LineSeries, LabelSeries, XAxis, YAxis } from 'react-vis';

class FitnessWeekGraph extends Component {
	render() {
		const data = this.convertWeeksToData(this.props.weeks);
		const chartWidth = 600;
		const chartHeight = 300;
		const chartDomain = [0, 100];
		return (
			<div>
				<h3>Total Miles</h3>
				<XYPlot
					xType="time"
					width={chartWidth}
					height={chartHeight}
					yDomain={chartDomain}
				>
					<XAxis />
					<YAxis />
					<LineSeries data={data} />
				</XYPlot>
			</div>
		);
	}

	convertWeeksToData(weeks) {
		var toReturn = [];
		var newWeekPoint;
		weeks.sort(this.compare);
		for (var i = 0; i < weeks.length; i++) {
			newWeekPoint = {};
			newWeekPoint.x = new Date(weeks[i].dateRecorded);
			newWeekPoint.y = weeks[i].totalMiles;
			newWeekPoint.label = weeks[i].totalMiles.toString();
			toReturn.push(newWeekPoint);
		}

		console.log(toReturn);
		return toReturn;
	}

	//TODO: put this somewhere else like a util function
	compare(a, b) {
		if (a.dateRecorded > b.dateRecorded) return 1;
		if (b.dateRecorded > a.dateRecorded) return -1;

		return 0;
	}


}

export default FitnessWeekGraph