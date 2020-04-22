import React, { Component } from 'react';
import TimeUtil from './time-util';

//show a this year to last year comparison given two values passed as props
class FitnessWeekCompareSumReport extends Component {
	render() {
		if (this.props.thisYearValue && this.props.lastYearValue) {
			const sumDiff = this.props.thisYearValue - this.props.lastYearValue;
			const arrow = (sumDiff >= 0 ? (<i className="fas fa-arrow-up has-text-success" />) : (<i className="fas fa-arrow-down has-text-danger" />))
			const displayDiff = this.displaySumDiffBasedOnType(sumDiff, this.props.displayType);
			return (
				<td>
					{arrow} {displayDiff} compared to last year.
				</td>
			);
		}
		else {
			return (
				<td></td>
			);
		}

	}

	displaySumDiffBasedOnType(diffValue, displayType) {
		const positiveDiffValue = Math.abs(diffValue);
		if (displayType === "time") {
			return TimeUtil.convertMinutes(positiveDiffValue);
		}
		else if (displayType === "decimal") {
			return positiveDiffValue.toFixed(1);
		}
		else {
			console.log("Could not translate display type for CompareSumReport: " + displayType);
			return positiveDiffValue;
		}
	}
}

export default FitnessWeekCompareSumReport;