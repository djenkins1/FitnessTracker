import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';
import { Container } from 'react-bulma-components';

class ErrorHandlerRedirect extends Component {
	render() {
		if (this.props.error) {
			return (
				<Redirect to="/error" />
			);
		}
		else {
			return (
				<Container>
					{this.props.children}
				</Container>
			);
		}
	}
}

export default ErrorHandlerRedirect;