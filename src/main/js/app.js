const React = require('react');
const ReactDOM = require('react-dom');
import FitnessWeekList from './fitness-week-list';

class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {fitnessWeeks: []};
	}

	componentDidMount() {
        fetch('./rest/fitnessWeeks')
        .then(res => res.json())
        .then((data) => {
          this.setState({ fitnessWeeks: data })
        })
        .catch(console.log)
	}

	render() {
		return (
			<FitnessWeekList weeks={this.state.fitnessWeeks}/>
		)
	}
}

ReactDOM.render(
		<App />,
		document.getElementById('react')
	)