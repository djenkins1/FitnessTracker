package com.djenkins.fitness.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import static org.hamcrest.Matchers.*;

import com.djenkins.fitness.domain.FitnessWeek;
import com.djenkins.fitness.service.FitnessWeekService;
import com.djenkins.fitness.util.FitnessWeekTestData;

import static org.mockito.Mockito.*;

@SpringBootTest
@WebAppConfiguration
public class TestFitnessWeekController {
	@MockBean
	FitnessWeekService fitnessWeekServiceMock;

	@Autowired
	FitnessWeekTestData testData;

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@BeforeEach
	public void setUp() {
		// We have to reset our mock between tests because the mock objects
		// are managed by the Spring container. If we would not reset them,
		// stubbing and verified behavior would "leak" from one test to another.
		Mockito.reset(fitnessWeekServiceMock);

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void testGetAllFitnessWeeks() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		when(fitnessWeekServiceMock.getAllFitnessWeek()).thenReturn(testDataResults);
		mockMvc.perform(get(FitnessWeekEndpointConstants.GET_ALL)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(testDataResults.size()))).andExpect(jsonPath("$[0].id", is(1)));
		verify(fitnessWeekServiceMock, times(1)).getAllFitnessWeek();
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testGetWeekBySpecificId() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		FitnessWeek testResult = testDataResults.get(0);
		Long id = testResult.getId();

		when(fitnessWeekServiceMock.getFitnessWeekById(id)).thenReturn(testResult);
		final String urlEndpoint = FitnessWeekEndpointConstants.GET_WEEK.replaceFirst("\\{id\\}", "" + id);
		mockMvc.perform(get(urlEndpoint)).andExpect(status().isOk()).andExpect(jsonPath("$.id", is(id.intValue())))
				.andExpect(jsonPath("$.totalTime", is(testResult.getTotalTime().intValue())))
				.andExpect(jsonPath("$.totalMiles", is(testResult.getTotalMiles())))
				.andExpect(jsonPath("$.totalCalories", is(testResult.getTotalCalories())))
				.andExpect(jsonPath("$.milesToDate", is(testResult.getMilesToDate().intValue())))
				.andExpect(jsonPath("$.daysExercised", is(testResult.getDaysExercised())))
				.andExpect(jsonPath("$.dateRecorded", is(testResult.getDateRecorded().toString())))
				.andExpect(jsonPath("$.exerciseType", is(testResult.getExerciseType())));

		verify(fitnessWeekServiceMock, times(1)).getFitnessWeekById(id);
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	// TODO: TEST GET BETWEEN DATES
	
	// TODO: TEST GET BETWEEN SAME DATE
	
	// TODO: TEST GET BY EXERCISE TYPES
	
	// TODO: TEST GET BY MULTIPLE IDS
	
	// TODO: TEST CREATE WEEK
}
