package com.djenkins.fitness.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djenkins.fitness.domain.FitnessWeek;
import com.djenkins.fitness.domain.FitnessWeekFilter;
import com.djenkins.fitness.repo.FitnessWeekRepository;
import com.djenkins.fitness.spec.FitnessWeekSpec;

@Service
public class FitnessWeekService {
	@Autowired
	FitnessWeekRepository fitnessWeekRepo;

	public List<FitnessWeek> getAllFitnessWeek() {
		List<FitnessWeek> toReturn = fitnessWeekRepo.findAll();
		return toReturn;
	}

	public FitnessWeek createFitnessWeek(FitnessWeek fitnessWeek) {
		return fitnessWeekRepo.save(fitnessWeek);
	}

	public FitnessWeek getFitnessWeekById(long weekId) {
		Optional<FitnessWeek> result = fitnessWeekRepo.findById(weekId);
		if (result.isPresent()) {
			return result.get();
		} else {
			throw new EntityNotFoundException("Could not find FitnessWeek with id: " + weekId );
		}
	}

	public List<FitnessWeek> getFitnessWeeksByFilter(FitnessWeekFilter filter) {
		List<FitnessWeek> results = fitnessWeekRepo.findAll(new FitnessWeekSpec(filter));
		return results;
	}

	public List<FitnessWeek> getByExerciseTypes(List<String> types) {
		FitnessWeekFilter filterByTypes = new FitnessWeekFilter();
		filterByTypes.setExerciseTypes(types);
		return getFitnessWeeksByFilter(filterByTypes);
	}

	public List<FitnessWeek> getByIds(List<Long> ids) {
		FitnessWeekFilter filterByIds = new FitnessWeekFilter();
		filterByIds.setIds(ids);
		return getFitnessWeeksByFilter(filterByIds);
	}

	public List<FitnessWeek> getInDateRange(Date startDate, Date endDate) {
		FitnessWeekFilter filterByDates = new FitnessWeekFilter();
		filterByDates.setFromDateRecorded(startDate);
		filterByDates.setToDateRecorded(endDate);
		return getFitnessWeeksByFilter(filterByDates);
	}
	
	public Double sumTotalCaloriesFor( List<FitnessWeek> weeks ) throws IllegalArgumentException {
		if ( weeks == null || weeks.isEmpty() ) {
			throw new IllegalArgumentException( "Parameter weeks must not be null or empty");
		}
		return weeks.stream().mapToDouble(o -> o.getTotalCalories()).sum();
	}
	
	public Double sumTotalMilesFor( List<FitnessWeek> weeks )  throws IllegalArgumentException {
		if ( weeks == null || weeks.isEmpty() ) {
			throw new IllegalArgumentException( "Parameter weeks must not be null or empty");
		}
		return weeks.stream().mapToDouble(o -> o.getTotalMiles()).sum();
	}
	
	public Long sumTotalTimeFor( List<FitnessWeek> weeks ) {
		if ( weeks == null || weeks.isEmpty() ) {
			throw new IllegalArgumentException( "Parameter weeks must not be null or empty");
		}
		return weeks.stream().mapToLong(o -> o.getTotalTime()).sum();
	}
}
