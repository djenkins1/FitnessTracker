package com.djenkins.fitness.spec;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.djenkins.fitness.domain.FitnessWeek;
import com.djenkins.fitness.domain.FitnessWeekFilter;
import com.djenkins.fitness.factory.FitnessWeekBuilder;
import com.djenkins.fitness.repo.FitnessWeekRepository;
import com.djenkins.fitness.util.DateTimeUtil;

import static org.mockito.Mockito.*;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class TestFitnessWeekSpec {
	@MockBean
	private FitnessWeekRepository mockedFitnessWeekRepo;

	@Autowired
	DateTimeUtil dateTimeUtil;

	@BeforeEach
	public void init() throws ParseException {
		List<FitnessWeek> allWeeks = new ArrayList<>();
		allWeeks.add(new FitnessWeekBuilder().withId(1L).withMilesToDate(120L).withExerciseType("Cycling")
				.withDateRecorded(dateTimeUtil.getDateFromStringMDY("2/28/2020")).withDaysExercised("M/T/W")
				.withTotalCalories(125.0).withTotalTime(120L).withTotalMiles(30.5).build());
		
		allWeeks.add(new FitnessWeekBuilder().withId(2L).withMilesToDate(180L).withExerciseType("Cycling")
				.withDateRecorded(dateTimeUtil.getDateFromStringMDY("3/2/2020")).withDaysExercised("F/S/S")
				.withTotalCalories(129.3).withTotalTime(120L).withTotalMiles(32.8).build());
		
		allWeeks.add(new FitnessWeekBuilder().withId(3L).withMilesToDate(180L).withExerciseType("Cycling")
				.withDateRecorded(dateTimeUtil.getDateFromStringMDY("3/5/2020")).withDaysExercised("M/T/T")
				.withTotalCalories(129.3).withTotalTime(120L).withTotalMiles(32.8).build());		
		when(mockedFitnessWeekRepo.findById(1L)).thenReturn(Optional.of(allWeeks.get(0)));
		when(mockedFitnessWeekRepo.findById(2L)).thenReturn(Optional.of(allWeeks.get(1)));
		when(mockedFitnessWeekRepo.findById(3L)).thenReturn(Optional.of(allWeeks.get(2)));
		when(mockedFitnessWeekRepo.findAll()).thenReturn(allWeeks);
		
	}
	
	@Test
	public void testMockedRepo() {
		Optional<FitnessWeek> week1 = mockedFitnessWeekRepo.findById( 1L );
		Optional<FitnessWeek> week2 = mockedFitnessWeekRepo.findById( 2L );
		List<FitnessWeek> allWeeks = mockedFitnessWeekRepo.findAll();
		assertEquals( week1.get().getId() , 1L );
		assertEquals( week2.get().getId() , 2L );
		assertTrue( allWeeks.size() >= 2 );
	}

	@Test
	public void testFilterByIds() {
		//TODO: rewrite this test to use mocked data
		List<Long> listOfIds = new ArrayList<>();
		listOfIds.add(1L);
		listOfIds.add(2L);
		FitnessWeekFilter filter = new FitnessWeekFilter();
		filter.setIds(listOfIds);

		List<FitnessWeek> results = mockedFitnessWeekRepo.findAll(new FitnessWeekSpec(filter));
		assertEquals(2, results.size());
	}

	// TODO: test filter by dates

	// TODO: test filter by days exercised

	// TODO: test filter by exercise types
}
