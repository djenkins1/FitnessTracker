import React, { Component } from 'react';
import { Box, Heading, Form, Button } from 'react-bulma-components';
import { Redirect } from 'react-router-dom';
import FieldValidHelper from './FieldValidHelper';
import Moment from 'moment';

class FitnessWeekForm extends Component {
	constructor(props) {
		super(props);
		this.state = {
			"id": (props.week && props.week.id ? props.week.id : 0),
			"totalTime": (props.week && props.week.totalTime ? props.week.totalTime : 0),
			"totalMiles": (props.week && props.week.totalMiles ? props.week.totalMiles : 0),
			"totalCalories": (props.week && props.week.totalCalories ? props.week.totalCalories : 0),
			"milesToDate": (props.week && props.week.milesToDate ? props.week.milesToDate : 0),
			"daysExercised": this.getDaysExercisedFromWeek(props.week),
			"dateRecorded": (props.week && props.week.dateRecorded ? props.week.dateRecorded : ''),
			"createdTs": (props.week && props.week.createdTs ? props.week.createdTs : ''),
			"exerciseType": (props.week && props.week.exerciseType ? props.week.exerciseType : "Cycling"),//default value
			"redirect": false,
			"formValid": false,
			"formErrors": {}
		};

		this.handleInputChange = this.handleInputChange.bind(this);
		this.handleSubmit = this.handleSubmit.bind(this);
		this.handleCancelClick = this.handleCancelClick.bind(this);
		this.handleCalorieAddClick = this.handleCalorieAddClick.bind(this);
		this.validateForm = this.validateForm.bind(this);
		this.validateField = this.validateField.bind(this);
		this.validateFieldAndChangeState = this.validateFieldAndChangeState.bind(this);
		this.createWeek = this.createWeek.bind(this);
		this.editWeek = this.editWeek.bind(this);
	}

	render() {
		if (this.state.redirect) {
			return (
				<Redirect to="/" />
			);
		}

		return (
			<Box>
				<Heading>{this.props.title}</Heading>
				<form onSubmit={this.handleSubmit} noValidate={true}>
					<Form.Field>
						<Form.Label htmlFor="totalTime">Total Time(Minutes)</Form.Label>
					</Form.Field>
					<Form.Field kind="addons">
						<Form.Control>
							<Form.Input type="number" name="totalTime" min="0" placeholder="120" value={this.state.totalTime} onChange={this.handleInputChange} />
						</Form.Control>
					</Form.Field>
					<Form.Field>
						<FieldValidHelper errors={this.state.formErrors["totalTime"]} />
					</Form.Field>
					<Form.Field>
						<Form.Label htmlFor="totalMiles">Total Miles</Form.Label>
					</Form.Field>
					<Form.Field kind="addons">
						<Form.Control>
							<Form.Input type="number" step="0.1" min="0" name="totalMiles" placeholder="20.5" value={this.state.totalMiles} onChange={this.handleInputChange} />
						</Form.Control>
					</Form.Field>
					<Form.Field>
						<FieldValidHelper errors={this.state.formErrors["totalMiles"]} />
					</Form.Field>
					<Form.Field>
						<Form.Label htmlFor="totalCalories">Total Calories</Form.Label>
					</Form.Field>
					<Form.Field kind="addons">
						<Form.Control>
							<Form.Input type="number" step="0.1" min="0" name="totalCalories" placeholder="250.25" value={this.state.totalCalories} onChange={this.handleInputChange} />
						</Form.Control>
						<Button outlined={true} tabIndex="-1" color="info" onClick={this.handleCalorieAddClick}>+999.9</Button>
					</Form.Field>
					<Form.Field>
						<FieldValidHelper errors={this.state.formErrors["totalCalories"]} />
					</Form.Field>
					<Form.Field>
						<Form.Label htmlFor="milesToDate">Miles to Date</Form.Label>
					</Form.Field>
					<Form.Field kind="addons">
						<Form.Control>
							<Form.Input type="number" min="0" name="milesToDate" placeholder="1250" value={this.state.milesToDate} onChange={this.handleInputChange} />
						</Form.Control>
					</Form.Field>
					<Form.Field>
						<FieldValidHelper errors={this.state.formErrors["milesToDate"]} />
					</Form.Field>
					<Form.Field>
						<Form.Label>Days Exercised</Form.Label>
						<Form.Control>
							<Form.Checkbox tabIndex="-1" name="monday" checked={this.state.daysExercised.monday} onChange={this.handleInputChange}>M</Form.Checkbox>
							<Form.Checkbox tabIndex="-1" name="tuesday" checked={this.state.daysExercised.tuesday} onChange={this.handleInputChange}>T</Form.Checkbox>
							<Form.Checkbox tabIndex="-1" name="wednesday" checked={this.state.daysExercised.wednesday} onChange={this.handleInputChange}>W</Form.Checkbox>
							<Form.Checkbox tabIndex="-1" name="thursday" checked={this.state.daysExercised.thursday} onChange={this.handleInputChange}>T</Form.Checkbox>
							<Form.Checkbox tabIndex="-1" name="friday" checked={this.state.daysExercised.friday} onChange={this.handleInputChange}>F</Form.Checkbox>
							<Form.Checkbox tabIndex="-1" name="saturday" checked={this.state.daysExercised.saturday} onChange={this.handleInputChange}>S</Form.Checkbox>
							<Form.Checkbox tabIndex="-1" name="sunday" checked={this.state.daysExercised.sunday} onChange={this.handleInputChange}>S</Form.Checkbox>
						</Form.Control>
						<FieldValidHelper errors={this.state.formErrors["daysExercised"]} />
					</Form.Field>
					<Form.Field>
						<Form.Label htmlFor="exerciseType">Exercise Type</Form.Label>
					</Form.Field>
					<Form.Field kind="addons">
						<Form.Control>
							<Form.Input type="text" name="exerciseType" placeholder="Cycling" value={this.state.exerciseType} onChange={this.handleInputChange} />
						</Form.Control>
					</Form.Field>
					<Form.Field>
						<FieldValidHelper errors={this.state.formErrors["exerciseType"]} />
					</Form.Field>
					<Form.Field>
						<Form.Label htmlFor="dateRecorded">Date Recorded</Form.Label>
					</Form.Field>
					<Form.Field kind="addons">
						<Form.Control>
							<Form.Input type="date" name="dateRecorded" placeholder="2019-12-25" value={this.state.dateRecorded} onChange={this.handleInputChange} />
						</Form.Control>
					</Form.Field>
					<Form.Field>
						<FieldValidHelper errors={this.state.formErrors["dateRecorded"]} />
					</Form.Field>
					<Form.Field kind="group">
						<Form.Control>
							<Button outlined={true} color="primary" loading={this.state.loading}>Submit</Button>
						</Form.Control>
						<Form.Control>
							<Button outlined={true} onClick={this.handleCancelClick} color="danger">Cancel</Button>
						</Form.Control>
					</Form.Field>
				</form>
			</Box>
		);
	}

	handleInputChange(event) {

		const target = event.target;
		const value = target.value;
		const name = target.name;
		if (name in this.state.daysExercised) {
			var daysExercised = { ...this.state.daysExercised }
			daysExercised[name] = target.checked;
			this.setState(
				{
					"daysExercised": daysExercised
				}, () => { this.validateFieldAndChangeState(name, target.checked) });
		}
		else {
			this.setState({
				[name]: value
			}, () => { this.validateFieldAndChangeState(name, value) });
		}
	}

	validateField(name, value) {
		const formErrors = { ...this.state.formErrors }
		var fieldTitle = "";
		var formAttr = "";
		if (name in this.state.daysExercised || name == "daysExercised") {
			formAttr = "daysExercised";
			fieldTitle = "Days Exercised";
			const daysExercised = this.state.daysExercised;
			const days = Object.keys(daysExercised);
			const daysChecked = days.filter(function (day) {
				return daysExercised[day]
			});
			if (daysChecked.length) {
				formErrors[formAttr] = [];//set to empty list if valid
			}
			else {
				//at least one day must be checked and none are checked currently
				formErrors[formAttr] = [
					fieldTitle + " must have at least one day selected."
				];
			}
		}
		else if (name === "totalTime" || name === "milesToDate") {
			formAttr = name;
			fieldTitle = (formAttr == "totalTime" ? "Total Time" : "Miles to Date");
			if (value === '') {
				//must not be empty
				formErrors[formAttr] = [
					fieldTitle + " must be a number."
				];
			}
			else if (!isFinite(String(value)) || !(Math.floor(value) == value)) {
				//must be numeric and integer
				formErrors[formAttr] = [
					fieldTitle + " must be a whole number(no decimal points)."
				];
			}
			else if (parseInt(value) < 0) {
				//must be greater than or equal to zero
				formErrors[formAttr] = [
					fieldTitle + " must be greater than or equal to zero."
				];
			}
			else {
				formErrors[formAttr] = [];//set to empty list if valid
			}
		}
		else if (name === "totalCalories" || name === "totalMiles") {
			fieldTitle = (name == "totalCalories" ? "Total Calories" : "Total Miles");
			formAttr = name;
			if (value === '') {
				//must not be empty
				formErrors[formAttr] = [
					fieldTitle + " must be a number."
				];
			}
			else if (!isFinite(String(value))) {
				//must be number
				formErrors[formAttr] = [
					fieldTitle + " must be numeric."
				];
			}
			else if (parseFloat(value) < 0) {
				//must be greater than or equal to zero
				formErrors[formAttr] = [
					fieldTitle + " must be greater than or equal to zero."
				];
			}
			else {
				formErrors[formAttr] = [];//set to empty list if valid
			}
		}
		else if (name === "dateRecorded") {
			formAttr = name;
			fieldTitle = "Date Recorded";
			const today = Moment();
			const valueAsMomentDate = Moment(value, "YYYY-MM-DD");
			if (!valueAsMomentDate.isValid()) {
				//must be a recognizable date
				formErrors[formAttr] = [
					fieldTitle + " must be a date of the format 2020-12-31."
				]
			}
			else if (valueAsMomentDate.isAfter(today)) {
				//must not be in future
				formErrors[formAttr] = [
					fieldTitle + " must not be in the future."
				]
			}
			else {
				formErrors[formAttr] = [];//set to empty list if valid
			}
		}
		else if (name === "exerciseType") {
			formAttr = name;
			fieldTitle = "Exercise Type";
			formErrors[formAttr] = [];//set to empty list if valid
			if (value.length == 0) {
				//must not be empty
				formErrors[formAttr] = [
					fieldTitle + " must not be empty."
				];
			}
			else if (value.length > 20) {
				//must not be more than 20 characters
				formErrors[formAttr] = [
					fieldTitle + " must not be more than 20 characters."
				];
			}
			else {
				formErrors[formAttr] = [];//set to empty list if valid
			}
		}
		else {
			console.log("Could not find validation for " + name + " with value " + value);
		}

		return formErrors[formAttr];
	}

	validateFieldAndChangeState(name, value) {
		const fieldErrors = this.validateField(name, value);
		const allErrors = { ...this.state.formErrors };
		const formAttr = (name in this.state.daysExercised ? "daysExercised" : name);
		allErrors[formAttr] = fieldErrors;
		this.setState({
			"formErrors": { ...allErrors }
		});
	}

	validateForm() {
		var isValid = true;
		const formErrors = {};
		formErrors["daysExercised"] = this.validateField("daysExercised", this.state.daysExercised);
		formErrors["totalTime"] = this.validateField("totalTime", this.state.totalTime);
		formErrors["totalMiles"] = this.validateField("totalMiles", this.state.totalMiles);
		formErrors["totalCalories"] = this.validateField("totalCalories", this.state.totalCalories);
		formErrors["milesToDate"] = this.validateField("milesToDate", this.state.milesToDate);
		formErrors["dateRecorded"] = this.validateField("dateRecorded", this.state.dateRecorded);
		formErrors["exerciseType"] = this.validateField("exerciseType", this.state.exerciseType);
		for (var key in formErrors) {
			if (formErrors[key] && formErrors[key].length > 0) {
				isValid = false;
				break;
			}
		}
		this.setState({ "formValid": isValid, "formErrors": formErrors });
		return isValid;
	}

	handleCalorieAddClick(event) {
		event.preventDefault();
		const oldCalories = (this.state.totalCalories != '' && isFinite(this.state.totalCalories) ? parseFloat(this.state.totalCalories) : 0);
		const newCalories = 999.9 + oldCalories;
		this.setState({
			"totalCalories": newCalories
		}, () => { this.validateFieldAndChangeState("totalCalories", newCalories) });
	}

	handleSubmit(event) {
		event.preventDefault();
		if (this.validateForm()) {
			//if week and week id are defined then this user is editing not creating
			if (this.props.week && this.props.week.id) {
				this.editWeek(event, this.state);
			}
			else {
				this.createWeek(event, this.state);
			}

		}
		else {
			console.log("validateForm() returned false");
		}
	}

	convertStateToFormData(stateData) {
		var toReturn = {
			"id": stateData.id,
			"totalTime": stateData.totalTime,
			"totalMiles": stateData.totalMiles,
			"totalCalories": stateData.totalCalories,
			"milesToDate": stateData.milesToDate,
			"dateRecorded": stateData.dateRecorded,
			"exerciseType": stateData.exerciseType,
			"createdTs": stateData.createdTs
		};

		var dayStr = "";
		var sep = "";
		for (var key in stateData.daysExercised) {
			if (stateData.daysExercised[key]) {
				dayStr += sep + key[0].toUpperCase();
				sep = "/";
			}
		}
		toReturn["daysExercised"] = dayStr;
		return toReturn;
	}

	editWeek(event, formData) {
		this.setState({ "loading": true });
		const url = "./rest/fitnessWeek/update";
		const editWeekData = this.convertStateToFormData(formData);
		fetch(url, {
			method: 'PUT',
			mode: 'cors',
			cache: 'no-cache',
			credentials: 'same-origin',
			headers: {
				'Content-Type': 'application/json'
			},
			redirect: 'error',
			referrerPolicy: 'no-referrer',
			body: JSON.stringify(editWeekData)
		})
			.then(res => {
				if (!res.ok) {
					if (res.status == 404) {
						throw Error("Problem editing week, could not find form endpoint.")
					}
					else if (res.status == 400) {
						//front end validation should catch anything bad in request
						//if it does not then show an error page
						throw Error("Validation failed for editing week.");
					}
					else {
						throw Error("An unexpected problem occurred, response code: " + res.status);
					}

				}
				else {
					res.json().then((updatedWeek) => {
						this.props.editWeek(updatedWeek);
						this.setState({ "redirect": true });
					});
				}
			})
			.catch((error) => {
				this.props.handleError(error);
			});
	}

	createWeek(event, formData) {
		this.setState({ "loading": true });
		const url = "./rest/fitnessWeeks";
		const newWeekData = this.convertStateToFormData(formData);
		fetch(url, {
			method: 'POST',
			mode: 'cors',
			cache: 'no-cache',
			credentials: 'same-origin',
			headers: {
				'Content-Type': 'application/json'
			},
			redirect: 'error',
			referrerPolicy: 'no-referrer',
			body: JSON.stringify(newWeekData)
		})
			.then(res => {
				if (!res.ok) {
					if (res.status == 404) {
						throw Error("Problem creating week, could not find form endpoint.")
					}
					else if (res.status == 400) {
						//front end validation should catch anything bad in request
						//if it does not then show an error page
						throw Error("Validation failed for creating week.");
					}
					else {
						throw Error("An unexpected problem occurred, response code: " + res.status);
					}

				}
				else {
					res.json().then((createdWeek) => {
						this.props.addWeek(createdWeek);
						this.setState({ "redirect": true });
					});
				}
			})
			.catch((error) => {
				this.props.handleError(error);
			});
	}

	handleCancelClick(event) {
		event.preventDefault();
		this.setState({ "redirect": true });
	}

	getDaysExercisedFromWeek(week) {

		const daysExercised = {
			"monday": false,
			"tuesday": false,
			"wednesday": false,
			"thursday": false,
			"friday": false,
			"saturday": false,
			"sunday": false
		};

		//if the week is defined then translate to object
		if (week && week.daysExercised) {
			const daysSplitBySlash = week.daysExercised.split("/");
			for (var index = 0; index < daysSplitBySlash.length; index++) {
				var day = daysSplitBySlash[index];
				switch (day) {
					case 'M':
						daysExercised["monday"] = true;
						break;
					case 'T':
						//could be tuesday or thursday
						if (daysExercised["tuesday"]) {
							daysExercised["thursday"] = true;
						}
						else {
							daysExercised["tuesday"] = true;
						}
						break;
					case 'W':
						daysExercised["wednesday"] = true;
						break;
					case 'F':
						daysExercised["friday"] = true;
						break;
					case 'S':
						//could be saturday or sunday
						if (daysExercised["saturday"]) {
							daysExercised["sunday"] = true;
						}
						else {
							daysExercised["saturday"] = true;
						}
						break;
					default:
						console.log("Could not translate the following to a day of the week: " + day);
						break;
				}
			}
		}
		else {
			//do nothing, the daysExercised object has default values so return it as normal
		}

		return daysExercised;
	}
}


export default FitnessWeekForm;
