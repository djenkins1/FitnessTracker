package com.djenkins.fitness.factory;

import java.sql.Date;
import java.sql.Timestamp;

import com.djenkins.fitness.domain.FitnessWeek;

public class FitnessWeekBuilder {
	private FitnessWeek fitnessWeek;
	private boolean isBuilt;
	public FitnessWeekBuilder() {
		this.fitnessWeek = new FitnessWeek();
		this.isBuilt = false;
	}
	
	public FitnessWeekBuilder withId( Long id ) {
		this.fitnessWeek.setId( id );
		return this;
	}
	
	public FitnessWeekBuilder withExerciseType( String exerciseType ) {
		this.fitnessWeek.setExerciseType(exerciseType);
		return this;
	}
	
	public FitnessWeekBuilder withTotalTime( Long totalTime ) {
		this.fitnessWeek.setTotalTime(totalTime);
		return this;
	}
	
	public FitnessWeekBuilder withTotalMiles( Double totalMiles ) {
		this.fitnessWeek.setTotalMiles( totalMiles );
		return this;
	}
	
	public FitnessWeekBuilder withTotalCalories( Double totalCalories ) {
		this.fitnessWeek.setTotalCalories(totalCalories);
		return this;
	}
	
	public FitnessWeekBuilder withMilesToDate( Long milesToDate ) {
		this.fitnessWeek.setMilesToDate(milesToDate);
		return this;
	}
	
	public FitnessWeekBuilder withDaysExercised( String daysExercised ) {
		this.fitnessWeek.setDaysExercised(daysExercised);
		return this;
	}
	
	public FitnessWeekBuilder withDateRecorded( Date dateRecorded ) {
		this.fitnessWeek.setDateRecorded(dateRecorded);
		return this;
	}
	
	public FitnessWeekBuilder withCreatedTs( Timestamp createdTs ) {
		this.fitnessWeek.setCreatedTs(createdTs);
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
