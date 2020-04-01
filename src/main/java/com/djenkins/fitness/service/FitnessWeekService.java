package com.djenkins.fitness.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djenkins.fitness.domain.FitnessWeek;
import com.djenkins.fitness.repo.FitnessWeekRepository;

@Service
public class FitnessWeekService {
	@Autowired
	FitnessWeekRepository fitnessWeekRepo;

	public List<FitnessWeek> getAllFitnessWeek() {
		List<FitnessWeek> toReturn = fitnessWeekRepo.findAll();
		return toReturn;
	}

	public FitnessWeek createFitnessWeek(FitnessWeek fitnessWeek) {
		return fitnessWeekRepo.save( fitnessWeek );
		
	}
}
