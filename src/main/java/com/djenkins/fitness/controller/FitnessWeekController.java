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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api()
public class FitnessWeekController {
	@Autowired
	private FitnessWeekService fitnessWeekService;

	@ApiOperation("Returns list of all FitnessWeek in the system.")
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
	@ApiOperation("Returns list of FitnessWeek that have recorded date within the date range given.")
	@RequestMapping(value = FitnessWeekEndpointConstants.GET_IN_RANGE, method = RequestMethod.GET)
	public List<FitnessWeek> getInRange(
			@ApiParam("Start date of the date range to search for. Cannot be empty.")
			@RequestParam
			Date startDate,
			@ApiParam("End date of the date range to search for. Cannot be empty.")
			@RequestParam
			Date endDate) {
		// get fitness weeks with recordedDate between startDate and endDate
		return fitnessWeekService.getInDateRange(startDate, endDate);
	}

	@ApiOperation("Returns list of FitnessWeek that have id within the list of ids given.")
	@RequestMapping(
			value = FitnessWeekEndpointConstants.GET_BY_IDS,
			method = RequestMethod.GET,
			produces = "application/json")
	public List<FitnessWeek> getByIds(
			@ApiParam("List of ids to search for. Cannot be empty.")
			@RequestParam
			List<Long> ids) {
		// get fitness weeks having ids given
		// TODO: validation, see Spring AOP
		return fitnessWeekService.getByIds(ids);
	}

	@ApiOperation("Returns list of FitnessWeek that have exercise type within the list of types given.")
	@RequestMapping(
			value = FitnessWeekEndpointConstants.GET_BY_EXERCISE_TYPE,
			method = RequestMethod.GET,
			produces = "application/json")
	public List<FitnessWeek> getByExerciseType(
			@ApiParam("List of exercise types to search for. Cannot be empty.")
			@RequestParam
			List<String> exerciseTypes) {
		// TODO: validation, see Spring AOP
		return fitnessWeekService.getByExerciseTypes(exerciseTypes);
	}

	@ApiOperation("Returns a specific FitnessWeek based on the identifier given. Gives 404 error if does not exist.")
	@RequestMapping(
			value = FitnessWeekEndpointConstants.GET_WEEK,
			method = RequestMethod.GET,
			produces = "application/json")
	public FitnessWeek getWeek(
			@ApiParam("Id of the FitnessWeek to search for. Cannot be empty.")
			@PathVariable("id")
			long weekId) {
		return fitnessWeekService.getFitnessWeekById(weekId);
	}

	@ApiOperation("Creates a new FitnessWeek.")
	@RequestMapping(
			value = FitnessWeekEndpointConstants.CREATE_WEEK,
			method = RequestMethod.POST,
			produces = "application/json")
	public @ResponseBody
	FitnessWeek createFitnessWeek(
			@ApiParam("Information for the new FitnessWeek to be created.")
			@RequestBody
			FitnessWeek fitnessWeek) {
		// TODO: validation, see Spring AOP
		fitnessWeek.setCreatedTs(Timestamp.from(Instant.now()));
		fitnessWeek.setId(null);// set to null in case passed into request
		return fitnessWeekService.createFitnessWeek(fitnessWeek);
	}

	@ApiOperation("Returns a FitnessWeekSum that contains the sum of total miles,total calories and total time of all FitnessWeek in the system.")
	@RequestMapping(
			value = FitnessWeekEndpointConstants.SUM_ALL,
			method = RequestMethod.GET,
			produces = "application/json")
	public @ResponseBody
	FitnessWeekSum sumAll() {
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
	@ApiOperation("Returns a FitnessWeekSum that contains the sum of total miles,total calories and total time of the FitnessWeek having recorded date with the date range given.")
	@RequestMapping(
			value = FitnessWeekEndpointConstants.SUM_IN_RANGE,
			method = RequestMethod.GET,
			produces = "application/json")
	public FitnessWeekSum sumInRange(
			@ApiParam("Start date of the date range to search for and sum. Cannot be empty.")
			@RequestParam
			Date startDate,
			@ApiParam("End date of the date range to search for and sum. Cannot be empty.")
			@RequestParam
			Date endDate) {
		List<FitnessWeek> weeksInRange = fitnessWeekService.getInDateRange(startDate, endDate);
		FitnessWeekSum sumReturn = new FitnessWeekSum();
		sumReturn.setTotalCalories(fitnessWeekService.sumTotalCaloriesFor(weeksInRange));
		sumReturn.setTotalMiles(fitnessWeekService.sumTotalMilesFor(weeksInRange));
		sumReturn.setTotalTime(fitnessWeekService.sumTotalTimeFor(weeksInRange));
		return sumReturn;
	}

	@ApiOperation("Returns a FitnessWeekSum that contains the sum of total miles,total calories and total time of the FitnessWeek having id within the list of ids given.")
	@RequestMapping(
			value = FitnessWeekEndpointConstants.SUM_BY_IDS,
			method = RequestMethod.GET,
			produces = "application/json")
	public FitnessWeekSum sumByIds(
			@ApiParam("List of ids to search by and sum. Cannot be empty.")
			@RequestParam
			List<Long> ids) {
		List<FitnessWeek> weeksByIds = fitnessWeekService.getByIds(ids);
		FitnessWeekSum sumReturn = new FitnessWeekSum();
		sumReturn.setTotalCalories(fitnessWeekService.sumTotalCaloriesFor(weeksByIds));
		sumReturn.setTotalMiles(fitnessWeekService.sumTotalMilesFor(weeksByIds));
		sumReturn.setTotalTime(fitnessWeekService.sumTotalTimeFor(weeksByIds));
		return sumReturn;
	}

	@ApiOperation("Returns a FitnessWeekSum that contains the sum of total miles,total calories and total time of the FitnessWeek having exercise type within the list of types given.")
	@RequestMapping(
			value = FitnessWeekEndpointConstants.SUM_BY_EXERCISE_TYPES,
			method = RequestMethod.GET,
			produces = "application/json")
	public FitnessWeekSum sumByExerciseTypes(
			@ApiParam("List of exercise types to search for and sum. Cannot be empty.")
			@RequestParam
			List<String> exerciseTypes) {
		List<FitnessWeek> results = fitnessWeekService.getByExerciseTypes(exerciseTypes);
		FitnessWeekSum sumReturn = new FitnessWeekSum();
		sumReturn.setTotalCalories(fitnessWeekService.sumTotalCaloriesFor(results));
		sumReturn.setTotalMiles(fitnessWeekService.sumTotalMilesFor(results));
		sumReturn.setTotalTime(fitnessWeekService.sumTotalTimeFor(results));
		return sumReturn;
	}
}
