const React = require('react');
import { Button } from "react-bulma-components";
class FitnessWeek extends React.Component {
	render() {
		const id = this.props.week.id;
		return (
			<tr>
				<td>{this.props.week.dateRecorded}</td>
				<td>{this.props.week.totalMiles}</td>
				<td>{this.props.week.totalCalories}</td>
				<td>{this.props.week.totalTime}</td>
				<td>{this.props.week.milesToDate}</td>
				<td>{this.props.week.exerciseType}</td>
				<td>{this.props.week.daysExercised}</td>
				<td><Button title="Delete" className="delete is-danger" onClick={() => { this.props.handleClickDelete(id) }} /></td>
			</tr>
		)
	}
}

export default FitnessWeek;
