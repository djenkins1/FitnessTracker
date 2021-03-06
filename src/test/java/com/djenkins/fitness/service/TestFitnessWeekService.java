package com.djenkins.fitness.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.djenkins.fitness.domain.FitnessWeek;
import com.djenkins.fitness.domain.FitnessWeekFilter;
import com.djenkins.fitness.domain.FitnessWeekSum;
import com.djenkins.fitness.factory.FitnessWeekBuilder;
import com.djenkins.fitness.repo.FitnessWeekRepository;
import com.djenkins.fitness.util.FitnessWeekTestData;

@SpringBootTest
public class TestFitnessWeekService {
	@Autowired
	FitnessWeekTestData testData;

	@Autowired
	FitnessWeekService fitnessWeekService;

	@MockBean
	FitnessWeekRepository mockedFitnessWeekRepo;

	private List<FitnessWeek> allWeeks;

	@BeforeEach
	public void init() throws ParseException {
		if (allWeeks == null) {
			allWeeks = testData.getAllData();
		}
		// setup mocked repo
		Mockito.reset(mockedFitnessWeekRepo);
		when(mockedFitnessWeekRepo.findById(1L)).thenReturn(Optional.of(allWeeks.get(0)));
		when(mockedFitnessWeekRepo.findById(2L)).thenReturn(Optional.of(allWeeks.get(1)));
		when(mockedFitnessWeekRepo.findById(3L)).thenReturn(Optional.of(allWeeks.get(2)));
		when(mockedFitnessWeekRepo.findAll()).thenReturn(allWeeks);
	}

	@Test
	public void testGetAllFitnessWeek() {
		assertEquals(allWeeks, fitnessWeekService.getAllFitnessWeek());

		// verify that the service only called the repo method once
		verify(mockedFitnessWeekRepo, times(1)).findAll();
		// verify that there were no other interactions with the repo
		verifyNoMoreInteractions(mockedFitnessWeekRepo);
	}

	@Test
	public void testCreateFitnessWeek() {
		// setup so that the repo.save method returns the first FitnessWeek in the list
		when(mockedFitnessWeekRepo.save(Mockito.any(FitnessWeek.class)))
				.thenReturn(allWeeks.get(0));
		FitnessWeek week = new FitnessWeekBuilder(allWeeks.get(0)).withId(null).build();
		FitnessWeek createdWeek = fitnessWeekService.createFitnessWeek(week);
		assertEquals(week.getDateRecorded(), createdWeek.getDateRecorded());
		assertEquals(week.getDaysExercised(), createdWeek.getDaysExercised());
		assertEquals(week.getCreatedTs(), createdWeek.getCreatedTs());
		assertEquals(week.getExerciseType(), createdWeek.getExerciseType());
		assertEquals(week.getMilesToDate(), createdWeek.getMilesToDate());
		assertEquals(week.getTotalCalories(), createdWeek.getTotalCalories());
		assertEquals(week.getTotalMiles(), createdWeek.getTotalMiles());
		assertTrue(week.getId() == null);
		assertNotNull(createdWeek.getId());

		// verify that the service only called the repo method once
		verify(mockedFitnessWeekRepo, times(1)).save(Mockito.any(FitnessWeek.class));
		// verify that there were no other interactions with the repo
		verifyNoMoreInteractions(mockedFitnessWeekRepo);
	}

	@Test
	public void testGetFitnessWeekByIdNotFound() {
		when(mockedFitnessWeekRepo.findById(0L)).thenReturn(Optional.ofNullable(null));
		Exception exception = assertThrows(EntityNotFoundException.class, () -> {
			fitnessWeekService.getFitnessWeekById(0L);
		});

		assertEquals("Could not find FitnessWeek with id: 0", exception.getMessage());

		// verify that the service only called the repo method once
		verify(mockedFitnessWeekRepo, times(1)).findById(Mockito.anyLong());
		// verify that there were no other interactions with the repo
		verifyNoMoreInteractions(mockedFitnessWeekRepo);
	}

	@Test
	public void testGetFitnessWeekByIdSuccess() {
		FitnessWeek firstWeek = allWeeks.get(0);
		FitnessWeek returned = fitnessWeekService.getFitnessWeekById(firstWeek.getId());
		assertEquals(firstWeek.getId(), returned.getId());
		assertEquals(firstWeek.getCreatedTs(), returned.getCreatedTs());
		assertEquals(firstWeek.getDateRecorded(), returned.getDateRecorded());
		assertEquals(firstWeek.getDaysExercised(), returned.getDaysExercised());
		assertEquals(firstWeek.getMilesToDate(), returned.getMilesToDate());
		assertEquals(firstWeek.getTotalCalories(), returned.getTotalCalories());
		assertEquals(firstWeek.getTotalMiles(), returned.getTotalMiles());
		assertEquals(firstWeek.getTotalTime(), returned.getTotalTime());

		// verify that the service only called the repo method once
		verify(mockedFitnessWeekRepo, times(1)).findById(Mockito.anyLong());
		// verify that there were no other interactions with the repo
		verifyNoMoreInteractions(mockedFitnessWeekRepo);
	}

	@Test
	public void testGetFitnessWeeksByFilter() {
		when(mockedFitnessWeekRepo.findAll(Mockito.any())).thenReturn(allWeeks);
		List<Long> ids = new ArrayList<>();
		ids.add(1L);
		ids.add(2L);
		ids.add(3L);
		FitnessWeekFilter filter = new FitnessWeekFilter();
		filter.setIds(ids);
		assertEquals(allWeeks, fitnessWeekService.getFitnessWeeksByFilter(filter));

		// verify that the service only called the repo method once
		verify(mockedFitnessWeekRepo, times(1)).findAll(Mockito.any());
		// verify that there were no other interactions with the repo
		verifyNoMoreInteractions(mockedFitnessWeekRepo);
	}

	@Test
	public void testGetByExerciseTypes() {
		when(mockedFitnessWeekRepo.findAll(Mockito.any())).thenReturn(allWeeks);

		List<String> types = new ArrayList<>();
		types.add("Cycling");
		types.add("Running");
		assertEquals(allWeeks, fitnessWeekService.getByExerciseTypes(types));

		// verify that the service only called the repo method once
		verify(mockedFitnessWeekRepo, times(1)).findAll(Mockito.any());
		// verify that there were no other interactions with the repo
		verifyNoMoreInteractions(mockedFitnessWeekRepo);
	}

	@Test
	public void testGetByIds() {
		when(mockedFitnessWeekRepo.findAll(Mockito.any())).thenReturn(allWeeks);
		List<Long> ids = new ArrayList<>();
		ids.add(1L);
		ids.add(2L);
		ids.add(3L);
		assertEquals(allWeeks, fitnessWeekService.getByIds(ids));

		// verify that the service only called the repo method once
		verify(mockedFitnessWeekRepo, times(1)).findAll(Mockito.any());
		// verify that there were no other interactions with the repo
		verifyNoMoreInteractions(mockedFitnessWeekRepo);
	}

	@Test
	public void testGetInDateRange() {
		LocalDate fromDate = allWeeks.get(0).getDateRecorded();
		LocalDate toDate = allWeeks.get(2).getDateRecorded();
		assertTrue(fromDate.compareTo(toDate) < 0);

		when(mockedFitnessWeekRepo.findAll(Mockito.any())).thenReturn(allWeeks);
		assertEquals(allWeeks, fitnessWeekService.getInDateRange(fromDate, toDate));

		// verify that the service only called the repo method once
		verify(mockedFitnessWeekRepo, times(1)).findAll(Mockito.any());
		// verify that there were no other interactions with the repo
		verifyNoMoreInteractions(mockedFitnessWeekRepo);
	}

	@Test
	public void testSumTotalCaloriesForEmptyListException() {
		List<FitnessWeek> emptyList = new ArrayList<>();
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			fitnessWeekService.sumTotalCaloriesFor(emptyList);
		});

		assertEquals("Parameter weeks must not be null or empty", e.getMessage());
		// verify that the repo was never called
		verifyNoMoreInteractions(mockedFitnessWeekRepo);
	}

	@Test
	public void testSumTotalCaloriesForNullListException() {
		List<FitnessWeek> nullList = null;
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			fitnessWeekService.sumTotalCaloriesFor(nullList);
		});

		assertEquals("Parameter weeks must not be null or empty", e.getMessage());
		// verify that the repo was never called
		verifyNoMoreInteractions(mockedFitnessWeekRepo);
	}

	@Test
	public void testSumTotalCaloriesForOne() {
		List<FitnessWeek> listOfOne = new ArrayList<>();
		FitnessWeek firstWeek = allWeeks.get(0);
		listOfOne.add(firstWeek);

		assertEquals(firstWeek.getTotalCalories(),
				fitnessWeekService.sumTotalCaloriesFor(listOfOne));
		// verify that the repo was never called
		verifyNoMoreInteractions(mockedFitnessWeekRepo);
	}

	@Test
	public void testSumTotalCaloriesForMany() {
		List<FitnessWeek> listOfMany = new ArrayList<>();
		FitnessWeek firstWeek = allWeeks.get(0);
		FitnessWeek secondWeek = allWeeks.get(1);
		listOfMany.add(firstWeek);
		listOfMany.add(secondWeek);

		assertEquals(firstWeek.getTotalCalories() + secondWeek.getTotalCalories(),
				fitnessWeekService.sumTotalCaloriesFor(listOfMany));
		// verify that the repo was never called
		verifyNoMoreInteractions(mockedFitnessWeekRepo);
	}

	@Test
	public void testSumTotalMilesForEmptyListException() {
		List<FitnessWeek> emptyList = new ArrayList<>();
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			fitnessWeekService.sumTotalMilesFor(emptyList);
		});

		assertEquals("Parameter weeks must not be null or empty", e.getMessage());
		// verify that the repo was never called
		verifyNoMoreInteractions(mockedFitnessWeekRepo);
	}

	@Test
	public void testSumTotalMilesForNullListException() {
		List<FitnessWeek> nullList = null;
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			fitnessWeekService.sumTotalMilesFor(nullList);
		});

		assertEquals("Parameter weeks must not be null or empty", e.getMessage());
		// verify that the repo was never called
		verifyNoMoreInteractions(mockedFitnessWeekRepo);
	}

	@Test
	public void testSumTotalMilesForOne() {
		List<FitnessWeek> listOfOne = new ArrayList<>();
		FitnessWeek firstWeek = allWeeks.get(0);
		listOfOne.add(firstWeek);

		assertEquals(firstWeek.getTotalMiles(), fitnessWeekService.sumTotalMilesFor(listOfOne));
		// verify that the repo was never called
		verifyNoMoreInteractions(mockedFitnessWeekRepo);
	}

	@Test
	public void testSumTotalMilesForMany() {
		List<FitnessWeek> listOfMany = new ArrayList<>();
		FitnessWeek firstWeek = allWeeks.get(0);
		FitnessWeek secondWeek = allWeeks.get(1);
		listOfMany.add(firstWeek);
		listOfMany.add(secondWeek);

		assertEquals(firstWeek.getTotalMiles() + secondWeek.getTotalMiles(),
				fitnessWeekService.sumTotalMilesFor(listOfMany));
		// verify that the repo was never called
		verifyNoMoreInteractions(mockedFitnessWeekRepo);
	}

	@Test
	public void testSumTotalTimeForEmptyListException() {
		List<FitnessWeek> emptyList = new ArrayList<>();
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			fitnessWeekService.sumTotalTimeFor(emptyList);
		});

		assertEquals("Parameter weeks must not be null or empty", e.getMessage());
		// verify that the repo was never called
		verifyNoMoreInteractions(mockedFitnessWeekRepo);
	}

	@Test
	public void testSumTotalTimeForNullListException() {
		List<FitnessWeek> nullList = null;
		Exception e = assertThrows(IllegalArgumentException.class, () -> {
			fitnessWeekService.sumTotalTimeFor(nullList);
		});

		assertEquals("Parameter weeks must not be null or empty", e.getMessage());
		// verify that the repo was never called
		verifyNoMoreInteractions(mockedFitnessWeekRepo);
	}

	@Test
	public void testSumTotalTimeForOne() {
		List<FitnessWeek> listOfOne = new ArrayList<>();
		FitnessWeek firstWeek = allWeeks.get(0);
		listOfOne.add(firstWeek);

		assertEquals(firstWeek.getTotalTime(), fitnessWeekService.sumTotalTimeFor(listOfOne));
		// verify that the repo was never called
		verifyNoMoreInteractions(mockedFitnessWeekRepo);
	}

	@Test
	public void testSumTotalTimeForMany() {
		List<FitnessWeek> listOfMany = new ArrayList<>();
		FitnessWeek firstWeek = allWeeks.get(0);
		FitnessWeek secondWeek = allWeeks.get(1);
		listOfMany.add(firstWeek);
		listOfMany.add(secondWeek);

		assertEquals(firstWeek.getTotalTime() + secondWeek.getTotalTime(),
				fitnessWeekService.sumTotalTimeFor(listOfMany));
		// verify that the repo was never called
		verifyNoMoreInteractions(mockedFitnessWeekRepo);
	}

	@Test
	public void testSumMonthlyForDateRangeOneMonth() {
		when(mockedFitnessWeekRepo.findAll(Mockito.any())).thenReturn(allWeeks);
		String startDateStr = "2020-01-01";
		String endDateStr = "2020-02-01";
		List<FitnessWeekSum> results = fitnessWeekService.sumMonthlyForDateRange(
				LocalDate.parse(startDateStr), LocalDate.parse(endDateStr));
		FitnessWeekSum sum = new FitnessWeekSum(fitnessWeekService.sumTotalMilesFor(allWeeks),
				fitnessWeekService.sumTotalCaloriesFor(allWeeks),
				fitnessWeekService.sumTotalTimeFor(allWeeks));
		assertEquals(1, results.size());
		assertEquals(sum.getTotalCalories(), results.get(0).getTotalCalories());
		assertEquals(sum.getTotalMiles(), results.get(0).getTotalMiles());
		assertEquals(sum.getTotalTime(), results.get(0).getTotalTime());
		assertEquals(startDateStr, results.get(0).getStartDate().toString());
		assertEquals(endDateStr, results.get(0).getEndDate().toString());
		// verify that the service only called the repo method once
		verify(mockedFitnessWeekRepo, times(1)).findAll(Mockito.any());
		verifyNoMoreInteractions(mockedFitnessWeekRepo);
	}

	@Test
	public void testSumMonthlyForDateRangeTwoMonths() {
		when(mockedFitnessWeekRepo.findAll(Mockito.any())).thenReturn(allWeeks);
		String startDateStr = "2020-01-01";
		String endDateStr = "2020-03-01";
		List<FitnessWeekSum> results = fitnessWeekService.sumMonthlyForDateRange(
				LocalDate.parse(startDateStr), LocalDate.parse(endDateStr));
		FitnessWeekSum sum = new FitnessWeekSum(fitnessWeekService.sumTotalMilesFor(allWeeks),
				fitnessWeekService.sumTotalCaloriesFor(allWeeks),
				fitnessWeekService.sumTotalTimeFor(allWeeks));
		assertEquals(2, results.size());
		for (FitnessWeekSum currentSum : results) {
			assertEquals(sum.getTotalCalories(), currentSum.getTotalCalories());
			assertEquals(sum.getTotalMiles(), currentSum.getTotalMiles());
			assertEquals(sum.getTotalTime(), currentSum.getTotalTime());
		}
		assertEquals(startDateStr, results.get(0).getStartDate().toString());
		assertEquals(LocalDate.parse(startDateStr).plusMonths(1).toString(),
				results.get(0).getEndDate().toString());
		assertEquals(LocalDate.parse(startDateStr).plusMonths(1).toString(),
				results.get(1).getStartDate().toString());
		assertEquals(LocalDate.parse(endDateStr).toString(),
				results.get(1).getEndDate().toString());
		// verify that the service only called the repo method once
		verify(mockedFitnessWeekRepo, times(2)).findAll(Mockito.any());
		verifyNoMoreInteractions(mockedFitnessWeekRepo);
	}

	@Test
	public void testSumMonthlyForDateRangeHalfMonth() {
		// If end date is less than one month from start date than endDate should match
		// and not be month later
		when(mockedFitnessWeekRepo.findAll(Mockito.any())).thenReturn(allWeeks);
		String startDateStr = "2020-01-01";
		String endDateStr = "2020-01-15";
		List<FitnessWeekSum> results = fitnessWeekService.sumMonthlyForDateRange(
				LocalDate.parse(startDateStr), LocalDate.parse(endDateStr));
		FitnessWeekSum sum = new FitnessWeekSum(fitnessWeekService.sumTotalMilesFor(allWeeks),
				fitnessWeekService.sumTotalCaloriesFor(allWeeks),
				fitnessWeekService.sumTotalTimeFor(allWeeks));
		assertEquals(1, results.size());
		assertEquals(sum.getTotalCalories(), results.get(0).getTotalCalories());
		assertEquals(sum.getTotalMiles(), results.get(0).getTotalMiles());
		assertEquals(sum.getTotalTime(), results.get(0).getTotalTime());
		assertEquals(startDateStr, results.get(0).getStartDate().toString());
		assertEquals(endDateStr, results.get(0).getEndDate().toString());
		// verify that the service only called the repo method once
		verify(mockedFitnessWeekRepo, times(1)).findAll(Mockito.any());
		verifyNoMoreInteractions(mockedFitnessWeekRepo);
	}

	@Test
	public void testSumMonthlyForDateRangeTwoMonthsPartway() {
		when(mockedFitnessWeekRepo.findAll(Mockito.any())).thenReturn(allWeeks);
		String startDateStr = "2020-01-01";
		String endDateStr = "2020-02-15";
		List<FitnessWeekSum> results = fitnessWeekService.sumMonthlyForDateRange(
				LocalDate.parse(startDateStr), LocalDate.parse(endDateStr));
		FitnessWeekSum sum = new FitnessWeekSum(fitnessWeekService.sumTotalMilesFor(allWeeks),
				fitnessWeekService.sumTotalCaloriesFor(allWeeks),
				fitnessWeekService.sumTotalTimeFor(allWeeks));
		assertEquals(2, results.size());
		for (FitnessWeekSum currentSum : results) {
			assertEquals(sum.getTotalCalories(), currentSum.getTotalCalories());
			assertEquals(sum.getTotalMiles(), currentSum.getTotalMiles());
			assertEquals(sum.getTotalTime(), currentSum.getTotalTime());
		}

		assertEquals(startDateStr, results.get(0).getStartDate().toString());
		assertEquals(LocalDate.parse(startDateStr).plusMonths(1).toString(),
				results.get(0).getEndDate().toString());
		assertEquals(LocalDate.parse(startDateStr).plusMonths(1).toString(),
				results.get(1).getStartDate().toString());
		assertEquals(LocalDate.parse(endDateStr).toString(),
				results.get(1).getEndDate().toString());
		// verify that the service only called the repo method once
		verify(mockedFitnessWeekRepo, times(2)).findAll(Mockito.any());
		verifyNoMoreInteractions(mockedFitnessWeekRepo);
	}

	@Test
	public void testDeleteFitnessWeekById() {
		Long id = 1L;
		fitnessWeekService.deleteFitnessWeekById(id);
		verify(mockedFitnessWeekRepo, times(1)).deleteById(id);
		verifyNoMoreInteractions(mockedFitnessWeekRepo);
	}

	@Test
	public void testUpdateFitnessWeek() {
		FitnessWeek weekToUpdate = allWeeks.get(0);
		when(mockedFitnessWeekRepo.save(Mockito.any())).thenReturn(weekToUpdate);
		FitnessWeek weekUpdated = fitnessWeekService.updateFitnessWeek(weekToUpdate);
		this.assertFitnessWeeksEqual(weekToUpdate, weekUpdated);
		verify(mockedFitnessWeekRepo, times(1)).save(Mockito.any());
		verifyNoMoreInteractions(mockedFitnessWeekRepo);
	}

	@Test
	public void testCreateFitnessWeeks() {
		List<FitnessWeek> weeksToCreate = allWeeks;
		when(mockedFitnessWeekRepo.saveAll(Mockito.anyList())).thenReturn(weeksToCreate);
		Iterable<FitnessWeek> weeksCreatedIterable = fitnessWeekService
				.createFitnessWeeks(weeksToCreate);
		List<FitnessWeek> weeksCreated = StreamSupport
				.stream(weeksCreatedIterable.spliterator(), false)
				.collect(Collectors.toList());
		assertEquals(weeksToCreate.size(), weeksCreated.size());
		for (int i = 0; i < weeksCreated.size(); i++) {
			this.assertFitnessWeeksEqual(weeksToCreate.get(i), weeksCreated.get(i));
		}
		verify(mockedFitnessWeekRepo, times(1)).saveAll(Mockito.anyList());
		verifyNoMoreInteractions(mockedFitnessWeekRepo);
	}

	private void assertFitnessWeeksEqual(FitnessWeek expected, FitnessWeek actual) {
		assertEquals(expected.getTotalCalories(), actual.getTotalCalories());
		assertEquals(expected.getTotalTime(), actual.getTotalTime());
		assertEquals(expected.getTotalMiles(), actual.getTotalMiles());
		assertEquals(expected.getCreatedTs(), actual.getCreatedTs());
		assertEquals(expected.getExerciseType(), actual.getExerciseType());
		assertEquals(expected.getMilesToDate(), actual.getMilesToDate());
		assertEquals(expected.getDateRecorded(), actual.getDateRecorded());
		assertEquals(expected.getId(), actual.getId());
	}
}
