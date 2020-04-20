import React, { Component } from 'react';
import { Box, Message, Container } from 'react-bulma-components';

class ErrorPage extends Component {
	render() {
		return (
			<Container>
				<Message color="danger">
					<Message.Header>Something went wrong!</Message.Header>
					<Message.Body>
						<p>{this.props.error}</p>
						<p>Please try again.</p>
					</Message.Body>
				</Message>
			</Container>
		);
	}
}

export default ErrorPage;