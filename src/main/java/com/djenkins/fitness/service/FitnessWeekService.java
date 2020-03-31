package com.djenkins.fitness.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.djenkins.fitness.domain.FitnessWeek;

@Service
public class FitnessWeekService {
	public List<FitnessWeek> getAllFitnessWeek(){
		List<FitnessWeek> toReturn = new ArrayList<>();
		//TODO: mock up data
		toReturn.add( new FitnessWeek() );
		toReturn.get( 0 ).setId( 1 );
		toReturn.get( 0 ).setExerciseType( "Cycling" );
		toReturn.get( 0 ).setTotalMiles( 500.0 );
		return toReturn;
	}
}
