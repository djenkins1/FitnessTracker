const React = require('react');
class FitnessWeek extends React.Component {
	render() {
		return (
			<tr>
				<td>{this.props.week.dateRecorded}</td>
				<td>{this.props.week.totalMiles}</td>
				<td>{this.props.week.totalCalories}</td>
				<td>{this.props.week.totalTime}</td>
				<td>{this.props.week.milesToDate}</td>
				<td>{this.props.week.exerciseType}</td>
				<td>{this.props.week.daysExercised}</td>
			</tr>
		)
	}
}

export default FitnessWeek;
