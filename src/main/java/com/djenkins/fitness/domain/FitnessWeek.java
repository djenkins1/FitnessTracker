package com.djenkins.fitness.domain;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FitnessWeek {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "week_id")
	private Long id;
	@Column(name = "total_time")
	private Long totalTime;
	@Column(name = "total_miles")
	private Double totalMiles;
	@Column(name = "total_calories")
	private Double totalCalories;
	@Column(name = "miles_to_date")
	private Long milesToDate;
	@Column(name = "days_exercised")
	private String daysExercised;
	@Column(name = "date_recorded")
	private Date dateRecorded;
	@Column(name = "exercise_type")
	private String exerciseType;
	@Column(name = "created_date")
	private Timestamp createdTs;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Date getDateRecorded() {
		return dateRecorded;
	}

	public void setDateRecorded(Date dateRecorded) {
		this.dateRecorded = dateRecorded;
	}

	public String getExerciseType() {
		return exerciseType;
	}

	public void setExerciseType(String exerciseType) {
		this.exerciseType = exerciseType;
	}

	public Timestamp getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Timestamp createdTs) {
		this.createdTs = createdTs;
	}

}
