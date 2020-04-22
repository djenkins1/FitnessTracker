package com.djenkins.fitness.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djenkins.fitness.domain.FitnessWeek;
import com.djenkins.fitness.domain.FitnessWeekFilter;
import com.djenkins.fitness.domain.FitnessWeekSum;
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

	public void deleteFitnessWeekById(Long id) {
		fitnessWeekRepo.deleteById(id);
	}

	public FitnessWeek updateFitnessWeek(FitnessWeek week) {
		return fitnessWeekRepo.save(week);
	}

	public Iterable<FitnessWeek> createFitnessWeeks(List<FitnessWeek> fitnessWeeks) {
		return fitnessWeekRepo.saveAll(fitnessWeeks);
	}

	public FitnessWeek getFitnessWeekById(long weekId) {
		Optional<FitnessWeek> result = fitnessWeekRepo.findById(weekId);
		if (result.isPresent()) {
			return result.get();
		} else {
			throw new EntityNotFoundException("Could not find FitnessWeek with id: " + weekId);
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

	public List<FitnessWeek> getInDateRange(LocalDate startDate, LocalDate endDate) {
		FitnessWeekFilter filterByDates = new FitnessWeekFilter();
		filterByDates.setFromDateRecorded(startDate);
		filterByDates.setToDateRecorded(endDate);
		return getFitnessWeeksByFilter(filterByDates);
	}

	public Double sumTotalCaloriesFor(List<FitnessWeek> weeks) throws IllegalArgumentException {
		if (weeks == null || weeks.isEmpty()) {
			throw new IllegalArgumentException("Parameter weeks must not be null or empty");
		}
		return weeks.stream().mapToDouble(o -> o.getTotalCalories()).sum();
	}

	public Double sumTotalMilesFor(List<FitnessWeek> weeks) throws IllegalArgumentException {
		if (weeks == null || weeks.isEmpty()) {
			throw new IllegalArgumentException("Parameter weeks must not be null or empty");
		}
		return weeks.stream().mapToDouble(o -> o.getTotalMiles()).sum();
	}

	public Long sumTotalTimeFor(List<FitnessWeek> weeks) {
		if (weeks == null || weeks.isEmpty()) {
			throw new IllegalArgumentException("Parameter weeks must not be null or empty");
		}
		return weeks.stream().mapToLong(o -> o.getTotalTime()).sum();
	}

	public List<FitnessWeekSum> sumMonthlyForDateRange(LocalDate startDate, LocalDate endDate) {
		List<FitnessWeek> weeks = null;
		FitnessWeekSum currentSum = null;
		List<FitnessWeekSum> resultSums = new ArrayList<>();
		LocalDate atDate = startDate;
		LocalDate nextMonth = atDate.plusMonths(1);
		// make sure that the nextMonth date does not go past the end date
		if (nextMonth.compareTo(endDate) > 0) {
			nextMonth = endDate;
		}
		// start at startDate and go through month by month getting weeks from each
		// month and sum them together for that month
		while (endDate.compareTo(atDate) > 0) {
			weeks = getInDateRange(atDate, nextMonth);
			currentSum = new FitnessWeekSum();
			currentSum.setStartDate(atDate);
			currentSum.setEndDate(nextMonth);
			if (!weeks.isEmpty()) {
				currentSum.setTotalCalories(sumTotalCaloriesFor(weeks));
				currentSum.setTotalMiles(sumTotalMilesFor(weeks));
				currentSum.setTotalTime(sumTotalTimeFor(weeks));
			} else {
				// Do nothing, sum will default to 0
			}

			resultSums.add(currentSum);
			atDate = atDate.plusMonths(1);
			nextMonth = atDate.plusMonths(1);
			// if the end date does not fall on the same day of the month as the start date
			// then set nextMonth to endDate when nextMonth is after endDate
			if (nextMonth.compareTo(endDate) > 0) {
				nextMonth = endDate;
			}
		}
		return resultSums;
	}
}
