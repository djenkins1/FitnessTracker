package com.djenkins.fitness.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(
		description = "Class representing the sum of miles,calories and time for a different weeks of physical exercise.")
public class FitnessWeekSum {
	@ApiModelProperty(
			notes = "Sum of total number of miles done in a number of weeks.",
			example = "150.5",
			required = true,
			position = 0)
	private Double totalMiles;

	@ApiModelProperty(
			notes = "Sum of total number of calories burned in a number of weeks.",
			example = "900.25",
			required = true,
			position = 1)
	private Double totalCalories;

	@ApiModelProperty(
			notes = "Sum of total number of minutes done in a number of weeks.",
			example = "500",
			required = true,
			position = 2)
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
