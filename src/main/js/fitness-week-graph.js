import React, { Component } from 'react';
import { XYPlot, LineSeries, LabelSeries, XAxis, YAxis } from 'react-vis';
import { Box, Heading } from 'react-bulma-components';

class FitnessWeekGraph extends Component {
	render() {
		const data = this.convertWeeksToData(this.props.weeks, this.props.showBy);
		const chartWidth = 600;//TODO: pass this in as prop
		const chartHeight = 300;//TODO: pass this in as prop
		const chartDomain = [0, 100];//TODO: make this dynamic this based on data
		return (
			<Box>
				<Heading>{this.props.title}</Heading>
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
			</Box>
		);
	}

	convertWeeksToData(weeks, showBy) {
		var toReturn = [];
		var newWeekPoint;
		weeks.sort(this.compare);
		for (var i = 0; i < weeks.length; i++) {
			newWeekPoint = {};
			newWeekPoint.x = new Date(weeks[i].dateRecorded);
			newWeekPoint.y = weeks[i][showBy];
			newWeekPoint.label = weeks[i][showBy].toString();
			toReturn.push(newWeekPoint);
		}

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