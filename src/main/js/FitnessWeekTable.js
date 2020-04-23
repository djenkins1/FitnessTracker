const React = require('react');
import FitnessWeek from './FitnessWeek';
import { Table, Heading, Container } from 'react-bulma-components';
import { Redirect } from 'react-router-dom';
import ConfirmModal from './ConfirmModal';

class FitnessWeekTable extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			"showModal": false,
			"deleteId": null,
			"redirectToEdit": false
		};

		this.handleClickYes = this.handleClickYes.bind(this);
		this.closeModal = this.closeModal.bind(this);
		this.handleClickDelete = this.handleClickDelete.bind(this);
		this.handleClickEdit = this.handleClickEdit.bind(this);
	}
	render() {
		if (this.state.redirectToEdit) {
			return (
				<Redirect to="/edit" />
			);
		}

		const weeks = this.props.weeks.map(week =>
			<FitnessWeek key={week.dateRecorded + "_" + week.id} week={week} handleClickDelete={this.handleClickDelete} handleClickEdit={this.handleClickEdit} />
		);
		const modalMessageBody = "Are you sure you wish to delete the week?";//TODO: show details of week
		return (
			<Container className="table-container padded">
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
							<th>Actions</th>
						</tr>
					</thead>
					<tbody>
						{weeks}
					</tbody>
				</Table>
				<ConfirmModal shown={this.state.showModal} yesBtnColor="danger" noBtnColor="info" onClose={this.closeModal} title="Delete Week" message={modalMessageBody} handleClickYes={this.handleClickYes} handleClickNo={this.closeModal} />
			</Container>
		)
	}

	handleClickDelete(id) {
		this.setState({ "showModal": true, "deleteId": id });
	}

	handleClickYes() {
		this.setState({ "showModal": false });
		this.props.handleClickDelete(this.state.deleteId);
	}

	closeModal() {
		this.setState({ "showModal": false });
	}

	handleClickEdit(id) {
		this.props.handleClickEdit(id);
		this.setState({ "redirectToEdit": true });
	}
}

export default FitnessWeekTable;
