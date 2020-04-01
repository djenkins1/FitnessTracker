package com.djenkins.fitness.domain;

import java.sql.Date;
import java.util.List;

/**
 * This class is meant to be used to keep filter criteria in order to filter the
 * FitnessWeek class results
 * 
 * @author Dilan Jenkins
 *
 */
public class FitnessWeekFilter {
	/**
	 * List of ids that should be included in the results,ignored if list is null or
	 * empty
	 */
	private List<Long> ids;
	/**
	 * List of exerciseTypes that the FitnessWeek should have,ignored if list if
	 * null or empty
	 */
	private List<String> exerciseTypes;
	/**
	 * List of days of the week combinations that the FitnessWeek should
	 * have,ignored if null or empty Example: S/M/T,W/T/F,...
	 */
	private List<String> daysExercised;
	/**
	 * The date that results should be included from, ignored if null or if
	 * toDateRecorded is null
	 */
	private Date fromDateRecorded;
	/**
	 * The date that results should be included up to,ignored if null or if
	 * fromDateRecorded is null
	 */
	private Date toDateRecorded;

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public List<String> getExerciseTypes() {
		return exerciseTypes;
	}

	public void setExerciseTypes(List<String> exerciseTypes) {
		this.exerciseTypes = exerciseTypes;
	}

	public List<String> getDaysExercised() {
		return daysExercised;
	}

	public void setDaysExercised(List<String> daysExercised) {
		this.daysExercised = daysExercised;
	}

	public Date getFromDateRecorded() {
		return fromDateRecorded;
	}

	public void setFromDateRecorded(Date fromDateRecorded) {
		this.fromDateRecorded = fromDateRecorded;
	}

	public Date getToDateRecorded() {
		return toDateRecorded;
	}

	public void setToDateRecorded(Date toDateRecorded) {
		this.toDateRecorded = toDateRecorded;
	}
}
