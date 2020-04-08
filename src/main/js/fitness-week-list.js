const React = require('react');
import FitnessWeek from './fitness-week';
class FitnessWeekList extends React.Component{
	render() {
		const weeks = this.props.weeks.map(week =>
			<FitnessWeek key={week.id} week={week}/>
		);
		return (
			<table>
				<tbody>
					<tr>
						<th>Total Miles</th>
						<th>Total Calories</th>
						<th>Total Time</th>
					</tr>
					{weeks}
				</tbody>
			</table>
		)
	}
}

export default FitnessWeekList;
