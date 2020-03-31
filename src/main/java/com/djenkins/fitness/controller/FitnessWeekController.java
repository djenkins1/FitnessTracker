package com.djenkins.fitness.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.djenkins.fitness.domain.FitnessWeek;
import com.djenkins.fitness.service.FitnessWeekService;

@RestController
public class FitnessWeekController {
	@Autowired
	private FitnessWeekService fitnessWeekService;
	
	@RequestMapping("home")
	public List<FitnessWeek> showData(){
		return fitnessWeekService.getAllFitnessWeek();
	}
}
