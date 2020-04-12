const React = require('react');
import FitnessWeek from './FitnessWeek';
import { Table, Heading, Container } from 'react-bulma-components';

class FitnessWeekTable extends React.Component {
	render() {
		const weeks = this.props.weeks.map(week =>
			<FitnessWeek key={week.id} week={week} />
		);
		return (
			<Container>
				<Heading>{this.props.title}</Heading>
				<Table>
					<thead>
						<tr>
							<th>Date</th>
							<th><abbr title="Total Miles">Miles</abbr></th>
							<th><abbr title="Total Calories">Calories</abbr></th>
							<th><abbr title="Total Time">Minutes</abbr></th>
							<th><abbr title="Miles To Date">MTD</abbr></th>
							<th><abbr title="Exercise Type">Type</abbr></th>
							<th><abbr title="Days Exercised">Days</abbr></th>
						</tr>
					</thead>
					<tbody>
						{weeks}
					</tbody>
				</Table>
			</Container>
		)
	}
}

export default FitnessWeekTable;
