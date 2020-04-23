const React = require('react');

class LoadingIcon extends React.Component {
	render() {
		if (this.props.isShown) {
			return (
				<div className="loader center">
					<i className="fa fa-cog fa-spin" />
				</div>
			);
		}
		else {
			return (
				<div></div>
			);
		}

	}
}

export default LoadingIcon;