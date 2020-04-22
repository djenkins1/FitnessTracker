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
				<td>
					<Button.Group size="small" hasAddons={true}>
						<Button outlined={true} title="Edit" color="info" onClick={() => { this.props.handleClickEdit(id) }}><i className="fas fa-edit"></i></Button>
						<Button outlined={true} title="Delete" color="danger" onClick={() => { this.props.handleClickDelete(id) }}><i className="fas fa-trash"></i></Button>
					</Button.Group>
				</td>
			</tr>
		)
	}
}

export default FitnessWeek;
