package com.djenkins.fitness.util;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.djenkins.fitness.domain.FitnessWeek;
import com.djenkins.fitness.factory.FitnessWeekBuilder;

@Component
public class FitnessWeekTestData {

	private List<FitnessWeek> allWeeks;

	public List<FitnessWeek> getAllData() throws ParseException {
		if (allWeeks == null) {
			allWeeks = new ArrayList<>();
			allWeeks.add(new FitnessWeekBuilder().withId(1L).withMilesToDate(120L)
					.withExerciseType("Cycling")
					.withDateRecorded(LocalDate.parse("2020-02-28")).withDaysExercised("M/T/W")
					.withTotalCalories(125.0).withTotalTime(120L).withTotalMiles(30.5)
					.withCreatedTs(Instant.now()).build());

			allWeeks.add(new FitnessWeekBuilder().withId(2L).withMilesToDate(180L)
					.withExerciseType("Cycling")
					.withDateRecorded(LocalDate.parse("2020-03-02")).withDaysExercised("F/S/S")
					.withTotalCalories(129.3).withTotalTime(120L).withTotalMiles(32.8)
					.withCreatedTs(Instant.now()).build());

			allWeeks.add(new FitnessWeekBuilder().withId(3L).withMilesToDate(180L)
					.withExerciseType("Cycling")
					.withDateRecorded(LocalDate.parse("2020-03-05")).withDaysExercised("M/T/T")
					.withTotalCalories(129.3).withTotalTime(120L).withTotalMiles(32.8)
					.withCreatedTs(Instant.now()).build());
		}

		return allWeeks;
	}

	public List<FitnessWeek> getDataByExerciseTypes(List<String> exerciseType)
			throws ParseException {
		getAllData();
		List<FitnessWeek> resultData = new ArrayList<>();
		for (FitnessWeek fitnessWeek : allWeeks) {
			if (exerciseType.contains(fitnessWeek.getExerciseType())) {
				resultData.add(fitnessWeek);
			}
		}
		return resultData;

	}
}
