import React, { Component } from 'react';
import FitnessWeekGraph from './fitness-week-graph';
import { Tabs, Container, Box } from 'react-bulma-components';

class FitnessWeekGraphFilter extends Component {
	constructor(props) {
		super(props);
		this.state = { "showBy": this.props.showAttrs[0].attr, "title": this.props.showAttrs[0].title };
	}
	render() {
		const tabs = this.props.showAttrs.map(attr =>
			<Tabs.Tab key={attr.id} color="primary">{attr.title}</Tabs.Tab>
		);
		return (
			<Container>
				<Tabs>
					{tabs}
				</Tabs>
				<FitnessWeekGraph title={this.state.title} showBy={this.state.showBy} weeks={this.props.weeks} />
			</Container>
		);
	}
}

export default FitnessWeekGraphFilter;