package com.djenkins.fitness.service;

import java.util.List;
import java.util.Optional;

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

	public FitnessWeek getFitnessWeekById(long weekId) {
		Optional<FitnessWeek> result = fitnessWeekRepo.findById(weekId);
		if ( result.isPresent() ) {
			return result.get();
		}
		else {
			return null;
		}
	}
}
