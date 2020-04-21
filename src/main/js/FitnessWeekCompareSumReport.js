import React, { Component } from 'react';

//show a this year to last year comparison given two values passed as props
class FitnessWeekCompareSumReport extends Component {
	render() {
		if (this.props.thisYearValue && this.props.lastYearValue) {
			const sumDiff = this.props.thisYearValue - this.props.lastYearValue;
			const arrow = (sumDiff >= 0 ? (<i className="fas fa-arrow-up has-text-success" />) : (<i className="fas fa-arrow-down has-text-danger" />))
			return (
				<td>
					{arrow} {Math.abs(sumDiff)} compared to last year.
				</td>
			);
		}
		else {
			return (
				<td></td>
			);
		}

	}
}

export default FitnessWeekCompareSumReport;