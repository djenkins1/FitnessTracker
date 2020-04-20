import React, { Component } from 'react';
import { Modal, Button } from "react-bulma-components";

class ConfirmModal extends Component {
	render() {
		return (
			<div>
				<Modal show={this.props.shown} onClose={this.props.onClose} closeOnBlur={true} showClose={false} {...this.props.modal}>
					<Modal.Card>
						<Modal.Card.Head showClose={false}>
							<Modal.Card.Title>
								{this.props.title}
							</Modal.Card.Title>
						</Modal.Card.Head>
						<Modal.Card.Body>
							{this.props.message}
						</Modal.Card.Body>
						<Modal.Card.Foot>
							<Button color={this.props.yesBtnColor} onClick={this.props.handleClickYes}>Yes</Button>
							<Button color={this.props.noBtnColor} onClick={this.props.handleClickNo}>No</Button>
						</Modal.Card.Foot>
					</Modal.Card>
				</Modal>
			</div>
		);
	}
}

export default ConfirmModal;