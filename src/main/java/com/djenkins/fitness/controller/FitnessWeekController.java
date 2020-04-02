package com.djenkins.fitness.controller;

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
import com.djenkins.fitness.service.FitnessWeekService;

@RestController
public class FitnessWeekController {
	@Autowired
	private FitnessWeekService fitnessWeekService;

	@RequestMapping(value = "/rest/fitnessWeeks", method = RequestMethod.GET)
	public List<FitnessWeek> getAll() {
		return fitnessWeekService.getAllFitnessWeek();
	}
	
	@RequestMapping(value = "/rest/fitnessWeeks/between", method = RequestMethod.GET)
	public List<FitnessWeek> getInRange( @RequestParam String startDate, @RequestParam String endDate ) {
		//TODO: get fitness weeks with recordedDate between startDate and endDate
		return fitnessWeekService.getAllFitnessWeek();
	}
	
	@RequestMapping(value = "/rest/fitnessWeeks/filter", method = RequestMethod.GET)
	public List<FitnessWeek> getByExerciseType( @RequestParam String exerciseType ){
		//TODO: get fitness weeks that match the filter provided
		return fitnessWeekService.getAllFitnessWeek();
	}
	
	@RequestMapping(value = "/rest/fitnessWeek/{id}", method = RequestMethod.GET)
	public FitnessWeek getWeek( @PathVariable("id") long weekId ) {
		//TODO: return error message if not found?
		return fitnessWeekService.getFitnessWeekById( weekId );
	}

	@RequestMapping(value = "/rest/fitnessWeeks", method = RequestMethod.POST)
	public @ResponseBody FitnessWeek createFitnessWeek(@RequestBody FitnessWeek fitnessWeek) {
		// TODO: validation, see Spring AOP
		fitnessWeek.setCreatedTs(Timestamp.from(Instant.now()));
		return fitnessWeekService.createFitnessWeek(fitnessWeek);
	}
}
