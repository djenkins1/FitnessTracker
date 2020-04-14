package com.djenkins.fitness.factory;

import java.time.Instant;
import java.time.LocalDate;

import com.djenkins.fitness.domain.FitnessWeek;

public class FitnessWeekBuilder {
	private FitnessWeek fitnessWeek;
	private boolean isBuilt;

	public FitnessWeekBuilder() {
		this.fitnessWeek = new FitnessWeek();
		this.isBuilt = false;
	}

	public FitnessWeekBuilder(FitnessWeek cloneFrom) {
		this.fitnessWeek = new FitnessWeek();
		withCreatedTs(cloneFrom.getCreatedTs()).withDateRecorded(cloneFrom.getDateRecorded())
				.withDaysExercised(cloneFrom.getDaysExercised()).withExerciseType(cloneFrom.getExerciseType())
				.withId(cloneFrom.getId()).withMilesToDate(cloneFrom.getMilesToDate())
				.withTotalCalories(cloneFrom.getTotalCalories()).withTotalMiles(cloneFrom.getTotalMiles())
				.withTotalTime(cloneFrom.getTotalTime());
	}

	public FitnessWeekBuilder withId(Long id) {
		this.fitnessWeek.setId(id);
		return this;
	}

	public FitnessWeekBuilder withExerciseType(String exerciseType) {
		this.fitnessWeek.setExerciseType(exerciseType);
		return this;
	}

	public FitnessWeekBuilder withTotalTime(Long totalTime) {
		this.fitnessWeek.setTotalTime(totalTime);
		return this;
	}

	public FitnessWeekBuilder withTotalMiles(Double totalMiles) {
		this.fitnessWeek.setTotalMiles(totalMiles);
		return this;
	}

	public FitnessWeekBuilder withTotalCalories(Double totalCalories) {
		this.fitnessWeek.setTotalCalories(totalCalories);
		return this;
	}

	public FitnessWeekBuilder withMilesToDate(Long milesToDate) {
		this.fitnessWeek.setMilesToDate(milesToDate);
		return this;
	}

	public FitnessWeekBuilder withDaysExercised(String daysExercised) {
		this.fitnessWeek.setDaysExercised(daysExercised);
		return this;
	}

	public FitnessWeekBuilder withDateRecorded(LocalDate dateRecorded) {
		this.fitnessWeek.setDateRecorded(dateRecorded);
		return this;
	}

	public FitnessWeekBuilder withCreatedTs(Instant instant) {
		this.fitnessWeek.setCreatedTs(instant);
		return this;
	}

	public FitnessWeek build() {
		this.isBuilt = true;
		return fitnessWeek;
	}

	public boolean isBuilt() {
		return isBuilt;
	}
}
