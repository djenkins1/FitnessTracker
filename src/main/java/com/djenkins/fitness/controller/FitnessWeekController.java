package com.djenkins.fitness.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.djenkins.fitness.domain.FitnessWeek;
import com.djenkins.fitness.domain.FitnessWeekSum;
import com.djenkins.fitness.service.FitnessWeekService;

@RestController
public class FitnessWeekController {
	@Autowired
	private FitnessWeekService fitnessWeekService;

	@RequestMapping(value = FitnessWeekEndpointConstants.GET_ALL, method = RequestMethod.GET)
	public List<FitnessWeek> getAll() {
		return fitnessWeekService.getAllFitnessWeek();
	}

	/**
	 * @param startDate A date in the format of YYYY-MM-dd
	 * @param endDate   A date in the format of YYYY-MM-dd
	 * @return List of FitnessWeek objects that have recordedDate between start date
	 *         and end date
	 */
	@RequestMapping(value = FitnessWeekEndpointConstants.GET_IN_RANGE, method = RequestMethod.GET)
	public List<FitnessWeek> getInRange(@RequestParam Date startDate, @RequestParam Date endDate) {
		// get fitness weeks with recordedDate between startDate and endDate
		return fitnessWeekService.getInDateRange(startDate, endDate);
	}

	@RequestMapping(value = FitnessWeekEndpointConstants.GET_BY_IDS, method = RequestMethod.GET)
	public List<FitnessWeek> getByIds(@RequestParam List<Long> ids) {
		// get fitness weeks having ids given
		// TODO: validation, see Spring AOP
		return fitnessWeekService.getByIds(ids);
	}

	@RequestMapping(value = FitnessWeekEndpointConstants.GET_BY_EXERCISE_TYPE, method = RequestMethod.GET)
	public List<FitnessWeek> getByExerciseType(@RequestParam List<String> exerciseTypes) {
		// TODO: validation, see Spring AOP
		return fitnessWeekService.getByExerciseTypes(exerciseTypes);
	}

	@RequestMapping(value = FitnessWeekEndpointConstants.GET_WEEK, method = RequestMethod.GET)
	public FitnessWeek getWeek(@PathVariable("id") long weekId) {
		return fitnessWeekService.getFitnessWeekById(weekId);
	}

	@RequestMapping(value = FitnessWeekEndpointConstants.CREATE_WEEK, method = RequestMethod.POST)
	public @ResponseBody FitnessWeek createFitnessWeek(@RequestBody FitnessWeek fitnessWeek) {
		// TODO: validation, see Spring AOP
		fitnessWeek.setCreatedTs(Timestamp.from(Instant.now()));
		fitnessWeek.setId(null);// set to null in case passed into request
		return fitnessWeekService.createFitnessWeek(fitnessWeek);
	}

	@RequestMapping(value = FitnessWeekEndpointConstants.SUM_ALL, method = RequestMethod.GET)
	public @ResponseBody FitnessWeekSum sumAll() {
		List<FitnessWeek> allWeeks = fitnessWeekService.getAllFitnessWeek();
		FitnessWeekSum sumReturn = new FitnessWeekSum();
		sumReturn.setTotalCalories(fitnessWeekService.sumTotalCaloriesFor(allWeeks));
		sumReturn.setTotalMiles(fitnessWeekService.sumTotalMilesFor(allWeeks));
		sumReturn.setTotalTime(fitnessWeekService.sumTotalTimeFor(allWeeks));
		return sumReturn;
	}

	/**
	 * @param startDate A date in the format of YYYY-MM-dd
	 * @param endDate   A date in the format of YYYY-MM-dd
	 * @return FitnessWeekSum object holding the sums for totalMiles,totalCalories
	 *         and totalTime
	 */
	@RequestMapping(value = FitnessWeekEndpointConstants.SUM_IN_RANGE, method = RequestMethod.GET)
	public FitnessWeekSum sumInRange(@RequestParam Date startDate, @RequestParam Date endDate) {
		List<FitnessWeek> weeksInRange = fitnessWeekService.getInDateRange(startDate, endDate);
		FitnessWeekSum sumReturn = new FitnessWeekSum();
		sumReturn.setTotalCalories(fitnessWeekService.sumTotalCaloriesFor(weeksInRange));
		sumReturn.setTotalMiles(fitnessWeekService.sumTotalMilesFor(weeksInRange));
		sumReturn.setTotalTime(fitnessWeekService.sumTotalTimeFor(weeksInRange));
		return sumReturn;
	}

	@RequestMapping(value = FitnessWeekEndpointConstants.SUM_BY_IDS, method = RequestMethod.GET)
	public FitnessWeekSum sumByIds(@RequestParam List<Long> ids) {
		List<FitnessWeek> weeksByIds = fitnessWeekService.getByIds(ids);
		FitnessWeekSum sumReturn = new FitnessWeekSum();
		sumReturn.setTotalCalories(fitnessWeekService.sumTotalCaloriesFor(weeksByIds));
		sumReturn.setTotalMiles(fitnessWeekService.sumTotalMilesFor(weeksByIds));
		sumReturn.setTotalTime(fitnessWeekService.sumTotalTimeFor(weeksByIds));
		return sumReturn;
	}

	@RequestMapping(value = FitnessWeekEndpointConstants.SUM_BY_EXERCISE_TYPES, method = RequestMethod.GET)
	public FitnessWeekSum sumByExerciseTypes(@RequestParam List<String> exerciseTypes) {
		List<FitnessWeek> results = fitnessWeekService.getByExerciseTypes(exerciseTypes);
		FitnessWeekSum sumReturn = new FitnessWeekSum();
		sumReturn.setTotalCalories(fitnessWeekService.sumTotalCaloriesFor(results));
		sumReturn.setTotalMiles(fitnessWeekService.sumTotalMilesFor(results));
		sumReturn.setTotalTime(fitnessWeekService.sumTotalTimeFor(results));
		return sumReturn;
	}
}
