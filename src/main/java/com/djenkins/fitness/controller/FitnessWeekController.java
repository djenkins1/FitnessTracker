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
import com.djenkins.fitness.domain.FitnessWeekFilter;
import com.djenkins.fitness.service.FitnessWeekService;

@RestController
public class FitnessWeekController {
	@Autowired
	private FitnessWeekService fitnessWeekService;

	@RequestMapping(value = "/rest/fitnessWeeks", method = RequestMethod.GET)
	public List<FitnessWeek> getAll() {
		return fitnessWeekService.getAllFitnessWeek();
	}

	/**
	 * @param startDate A date in the format of YYYY-MM-dd
	 * @param endDate A date in the format of YYYY-MM-dd
	 * @return List of FitnessWeek objects that have recordedDate between start date and end date
	 */
	@RequestMapping(value = "/rest/fitnessWeeks/between", method = RequestMethod.GET)
	public List<FitnessWeek> getInRange(@RequestParam Date startDate, @RequestParam Date endDate) {
		// get fitness weeks with recordedDate between startDate and endDate
		FitnessWeekFilter filterByDates = new FitnessWeekFilter();
		filterByDates.setFromDateRecorded(startDate);
		filterByDates.setToDateRecorded(endDate);
		return fitnessWeekService.getFitnessWeeksByFilter(filterByDates);
	}
	
	@RequestMapping(value = "/rest/fitnessWeeks/ids", method = RequestMethod.GET)
	public List<FitnessWeek> getByIds(@RequestParam List<Long> ids ) {
		// get fitness weeks having ids given
		// TODO: validation, see Spring AOP
		FitnessWeekFilter filterByIds = new FitnessWeekFilter();
		filterByIds.setIds(ids);
		return fitnessWeekService.getFitnessWeeksByFilter(filterByIds);
	}

	@RequestMapping(value = "/rest/fitnessWeeks/exerciseTypes", method = RequestMethod.GET)
	public List<FitnessWeek> getByExerciseType(@RequestParam List<String> exerciseTypes ) {
		// TODO: validation, see Spring AOP
		FitnessWeekFilter filterByTypes = new FitnessWeekFilter();
		filterByTypes.setExerciseTypes( exerciseTypes );
		return fitnessWeekService.getFitnessWeeksByFilter(filterByTypes);
	}

	@RequestMapping(value = "/rest/fitnessWeek/{id}", method = RequestMethod.GET)
	public FitnessWeek getWeek(@PathVariable("id") long weekId) {
		// TODO: return error message if not found?
		return fitnessWeekService.getFitnessWeekById(weekId);
	}

	@RequestMapping(value = "/rest/fitnessWeeks", method = RequestMethod.POST)
	public @ResponseBody FitnessWeek createFitnessWeek(@RequestBody FitnessWeek fitnessWeek) {
		// TODO: validation, see Spring AOP
		fitnessWeek.setCreatedTs(Timestamp.from(Instant.now()));
		return fitnessWeekService.createFitnessWeek(fitnessWeek);
	}
}
