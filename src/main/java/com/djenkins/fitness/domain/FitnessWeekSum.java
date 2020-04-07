package com.djenkins.fitness.domain;

public class FitnessWeekSum {
	private Double totalMiles;

	private Double totalCalories;

	private Long totalTime;

	public FitnessWeekSum() {
		this.totalCalories = 0.0;
		this.totalMiles = 0.0;
		this.totalTime = 0L;
	}

	public FitnessWeekSum(Double totalMiles, Double totalCalories, Long totalTime) {
		this.totalMiles = totalMiles;
		this.totalTime = totalTime;
		this.totalCalories = totalCalories;
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

	public Long getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Long totalTime) {
		this.totalTime = totalTime;
	}

}
