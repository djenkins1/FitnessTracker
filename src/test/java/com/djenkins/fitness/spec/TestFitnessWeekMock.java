package com.djenkins.fitness.spec;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.djenkins.fitness.domain.FitnessWeek;
import com.djenkins.fitness.repo.FitnessWeekRepository;
import com.djenkins.fitness.util.FitnessWeekTestData;

@SpringBootTest
public class TestFitnessWeekMock {
	@MockBean
	private FitnessWeekRepository mockedFitnessWeekRepo;

	@Autowired
	FitnessWeekTestData testData;

	@BeforeEach
	public void init() throws ParseException {
		List<FitnessWeek> allWeeks = testData.getAllData();
		// setup mocked repo
		when(mockedFitnessWeekRepo.findById(1L)).thenReturn(Optional.of(allWeeks.get(0)));
		when(mockedFitnessWeekRepo.findById(2L)).thenReturn(Optional.of(allWeeks.get(1)));
		when(mockedFitnessWeekRepo.findById(3L)).thenReturn(Optional.of(allWeeks.get(2)));
		when(mockedFitnessWeekRepo.findAll()).thenReturn(allWeeks);
	}

	@Test
	public void testMockedRepo() {
		Optional<FitnessWeek> week1 = mockedFitnessWeekRepo.findById(1L);
		Optional<FitnessWeek> week2 = mockedFitnessWeekRepo.findById(2L);
		List<FitnessWeek> allWeeks = mockedFitnessWeekRepo.findAll();
		assertEquals(week1.get().getId(), 1L);
		assertEquals(week2.get().getId(), 2L);
		assertTrue(allWeeks.size() >= 2);
	}
}
