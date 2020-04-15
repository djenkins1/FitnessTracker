package com.djenkins.fitness.domain;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

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

	@ApiModelProperty(
			notes = "Starting date for the date range.",
			example = "2020-03-20",
			required = false,
			position = 3)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate startDate;

	@ApiModelProperty(
			notes = "Ending date for the date range.",
			example = "2020-04-25",
			required = false,
			position = 4)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate endDate;

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

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

}
