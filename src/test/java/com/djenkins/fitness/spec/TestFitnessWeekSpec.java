package com.djenkins.fitness.spec;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.djenkins.fitness.domain.FitnessWeek;
import com.djenkins.fitness.domain.FitnessWeekFilter;
import com.djenkins.fitness.repo.FitnessWeekRepository;

@SpringBootTest
@Disabled
public class TestFitnessWeekSpec {
	@Autowired
	private FitnessWeekRepository fitnessWeekRepo;

	@Test
	public void testFilterByIds() {
		List<Long> listOfIds = new ArrayList<>();
		listOfIds.add(1L);
		listOfIds.add(2L);
		FitnessWeekFilter filter = new FitnessWeekFilter();
		filter.setIds(listOfIds);

		List<FitnessWeek> results = fitnessWeekRepo.findAll(new FitnessWeekSpec(filter));
		assertEquals(2, results.size());
		for (FitnessWeek week : results) {
			assertTrue(listOfIds.contains(week.getId()));
		}
	}

	@Test
	public void testFilterByExerciseTypes() {
		List<String> listOfTypes = new ArrayList<>();
		listOfTypes.add("Cycling");
		FitnessWeekFilter filter = new FitnessWeekFilter();
		filter.setExerciseTypes(listOfTypes);
		List<FitnessWeek> results = fitnessWeekRepo.findAll(new FitnessWeekSpec(filter));
		for (FitnessWeek week : results) {
			assertTrue(listOfTypes.contains(week.getExerciseType()));
		}
		assertTrue(results.size() > 0);
	}

	@Test
	public void testFilterByExerciseTypesNoData() {
		List<String> listOfTypes = new ArrayList<>();
		listOfTypes.add("Yoga");
		FitnessWeekFilter filter = new FitnessWeekFilter();
		filter.setExerciseTypes(listOfTypes);
		List<FitnessWeek> results = fitnessWeekRepo.findAll(new FitnessWeekSpec(filter));
		assertEquals(0, results.size());
	}

	@Test
	public void testFilterByFromAndToDates() throws ParseException {
		LocalDate fromDate = LocalDate.parse("2/27/2020");
		LocalDate toDate = LocalDate.parse("3/20/2020");
		FitnessWeekFilter filter = new FitnessWeekFilter();
		filter.setFromDateRecorded(fromDate);
		filter.setToDateRecorded(toDate);
		List<FitnessWeek> results = fitnessWeekRepo.findAll(new FitnessWeekSpec(filter));
		assertTrue(results.size() > 0);
		for (FitnessWeek week : results) {
			// verify that the date recorded is between fromDate and toDate
			assertTrue(week.getDateRecorded().compareTo(fromDate) >= 0);
			assertTrue(week.getDateRecorded().compareTo(toDate) <= 0);
		}
	}

	@Test
	public void testFilterByDaysExercised() {
		FitnessWeekFilter filter = new FitnessWeekFilter();
		List<String> daysExercised = new ArrayList<>();
		daysExercised.add("W/T/F");
		filter.setDaysExercised(daysExercised);
		List<FitnessWeek> results = fitnessWeekRepo.findAll(new FitnessWeekSpec(filter));
		assertTrue(results.size() > 0);
		for (FitnessWeek week : results) {
			assertTrue(daysExercised.contains(week.getDaysExercised()));
		}
	}

}
