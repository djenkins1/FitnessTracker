import React, { Component } from 'react';
import { Form } from 'react-bulma-components';

class FieldValidHelper extends Component {
	render() {
		if (this.props.errors && this.props.errors.length > 0) {
			return (
				<Form.Help color="danger">
					{this.props.errors[0]}
				</Form.Help>
			);
		}
		else {
			return (
				<Form.Help />
			);
		}

	}
}

export default FieldValidHelper;
