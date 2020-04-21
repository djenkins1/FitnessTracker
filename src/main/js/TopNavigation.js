const React = require('react');
import { Navbar } from "react-bulma-components";
import { NavLink } from "react-router-dom";

class TopNavigation extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			"isHamburgerActive": false
		};
		this.handleHamburgerClick = this.handleHamburgerClick.bind(this);
	}

	render() {
		return (
			<Navbar color="info">
				<Navbar.Brand>
					<Navbar.Item renderAs="span"><i className="fas fa-running"></i> Fitness Tracker</Navbar.Item>
					<Navbar.Burger data-target="navLinksMenu" className={this.state.isHamburgerActive ? "is-active" : ""}
						onClick={this.handleHamburgerClick} />
				</Navbar.Brand>
				<Navbar.Menu className={this.state.isHamburgerActive ? "is-active" : ""}>
					<Navbar.Container>
						<NavLink exact activeClassName="is-active" onClick={this.props.onClick} className="is-tab navbar-item" to="/index" >Home</NavLink>
						<NavLink exact activeClassName="is-active" onClick={this.props.onClick} className="is-tab navbar-item" to="/graph" >Daily Graph</NavLink>
						<NavLink exact activeClassName="is-active" onClick={this.props.onClick} className="is-tab navbar-item" to="/sums">Monthly Graph</NavLink>
						<NavLink exact activeClassName="is-active" onClick={this.props.onClick} className="is-tab navbar-item" to="/sumsAnnual" >Annual Report</NavLink>
						<NavLink exact activeClassName="is-active" onClick={this.props.onClick} className="is-tab navbar-item" to="/create" >Add Week</NavLink>
					</Navbar.Container>
				</Navbar.Menu>
			</Navbar>
		);
	}

	handleHamburgerClick(event) {
		event.preventDefault();
		this.setState({ "isHamburgerActive": !this.state.isHamburgerActive });
	}

}

export default TopNavigation;
