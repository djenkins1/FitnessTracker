package com.djenkins.fitness.domain;

public class FitnessWeek {
	private int id;
	private Long totalTime;
	private Double totalMiles;
	private Double totalCalories;
	private Long milesToDate;
	private String daysExercised;
	private String dateRecorded;
	private String exerciseType;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Long totalTime) {
		this.totalTime = totalTime;
	}

	public Double getTotalMiles() {
		return totalMiles;
	}

	public void setTotalMiles(Double totalMiles) {
		this.totalMiles = totalMiles;
	}

	public Double getTotalCalories() {
		return totalCalories;
	}

	public void setTotalCalories(Double totalCalories) {
		this.totalCalories = totalCalories;
	}

	public Long getMilesToDate() {
		return milesToDate;
	}

	public void setMilesToDate(Long milesToDate) {
		this.milesToDate = milesToDate;
	}

	public String getDaysExercised() {
		return daysExercised;
	}

	public void setDaysExercised(String daysExercised) {
		this.daysExercised = daysExercised;
	}

	public String getDateRecorded() {
		return dateRecorded;
	}

	public void setDateRecorded(String dateRecorded) {
		this.dateRecorded = dateRecorded;
	}

	public String getExerciseType() {
		return exerciseType;
	}

	public void setExerciseType(String exerciseType) {
		this.exerciseType = exerciseType;
	}

}
