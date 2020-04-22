import React, { Component } from 'react';
import FitnessWeekGraph from './FitnessWeekGraph';
import { Tabs, Box } from 'react-bulma-components';

class FitnessWeekGraphFilter extends Component {
	constructor(props) {
		super(props);
		this.state = { "activeTab": 0, "showBy": this.props.showAttrs[0].attr, "title": this.props.showAttrs[0].title };
	}
	render() {
		const tabs = this.convertAttrsToTabs(this.props.showAttrs);
		return (
			<Box>
				<Tabs>
					{tabs}
				</Tabs>
				<FitnessWeekGraph chartWidth={700} chartHeight={350} title={this.state.title} showByX={this.props.showByX} showByY={this.state.showBy} weeks={this.props.weeks} />
				{this.props.children}
			</Box>
		);
	}
	convertAttrsToTabs(attrs) {
		var toReturn = [];
		var attr;
		for (var i = 0; i < attrs.length; i++) {
			attr = attrs[i];
			if (this.state.activeTab == attr.id) {
				toReturn.push(
					<Tabs.Tab onClick={this.changeShown.bind(this, i)} key={attr.id} active color="primary">{attr.title}</Tabs.Tab>
				);
			}
			else {
				toReturn.push(
					<Tabs.Tab onClick={this.changeShown.bind(this, i)} key={attr.id} color="primary">{attr.title}</Tabs.Tab>
				);
			}
		}
		return toReturn;

	}
	changeShown(index, e) {
		this.setState({
			"activeTab": index,
			"showBy": this.props.showAttrs[index].attr,
			"title": this.props.showAttrs[index].title
		});
	}
}

export default FitnessWeekGraphFilter;