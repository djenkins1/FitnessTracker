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
		this.handleLinkClick = this.handleLinkClick.bind(this);
	}

	render() {
		return (
			<Navbar color="info" fixed="top">
				<Navbar.Brand>
					<Navbar.Item renderAs="span">
						<span className="icon">
							<i className="fas fa-running"></i>
						</span>
						<span>Fitness Tracker</span>
					</Navbar.Item>
					<Navbar.Burger data-target="navLinksMenu" className={this.state.isHamburgerActive ? "is-active" : ""}
						onClick={this.handleHamburgerClick} />
				</Navbar.Brand>
				<Navbar.Menu className={this.state.isHamburgerActive ? "is-active" : ""}>
					<Navbar.Container>
						<NavLink exact activeClassName="is-active" onClick={this.handleLinkClick} className="is-tab navbar-item" to="/index" >Home</NavLink>
						<NavLink exact activeClassName="is-active" onClick={this.handleLinkClick} className="is-tab navbar-item" to="/graph" >Daily Graph</NavLink>
						<NavLink exact activeClassName="is-active" onClick={this.handleLinkClick} className="is-tab navbar-item" to="/sums">Monthly Graph</NavLink>
						<NavLink exact activeClassName="is-active" onClick={this.handleLinkClick} className="is-tab navbar-item" to="/sumsAnnual" >Annual Report</NavLink>
						<NavLink exact activeClassName="is-active" onClick={this.handleLinkClick} className="is-tab navbar-item" to="/create" >Add Week</NavLink>
					</Navbar.Container>
				</Navbar.Menu>
			</Navbar>
		);
	}

	handleHamburgerClick(event) {
		event.preventDefault();
		this.setState({ "isHamburgerActive": !this.state.isHamburgerActive });
	}

	handleLinkClick(event) {
		this.setState({ "isHamburgerActive": false });
		this.props.onClick(event);
	}

}

export default TopNavigation;
