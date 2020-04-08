const React = require('react');
class FitnessWeek extends React.Component{
	render() {
		return (
			<tr>
				<td>{this.props.week.totalMiles}</td>
				<td>{this.props.week.totalCalories}</td>
				<td>{this.props.week.totalTime}</td>
			</tr>
		)
	}
}

export default FitnessWeek;
