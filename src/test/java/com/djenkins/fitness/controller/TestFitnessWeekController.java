package com.djenkins.fitness.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import javax.persistence.EntityNotFoundException;

import static org.hamcrest.Matchers.*;

import com.djenkins.fitness.domain.FitnessWeek;
import com.djenkins.fitness.domain.FitnessWeekSum;
import com.djenkins.fitness.factory.FitnessWeekBuilder;
import com.djenkins.fitness.service.FitnessWeekService;
import com.djenkins.fitness.util.FitnessWeekTestData;
import com.djenkins.fitness.util.TestUtil;

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

	@Autowired
	private TestUtil testUtil;

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
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(testDataResults.size())))
				.andExpect(jsonPath("$[0].id", is(testDataResults.get(0).getId().intValue())));

		// verify that the method was only called once
		verify(fitnessWeekServiceMock, times(1)).getAllFitnessWeek();
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testGetWeekBySpecificIdSuccess() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		FitnessWeek testResult = testDataResults.get(0);
		Long id = testResult.getId();

		when(fitnessWeekServiceMock.getFitnessWeekById(id)).thenReturn(testResult);
		mockMvc.perform(get(FitnessWeekEndpointConstants.GET_WEEK, id)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is(id.intValue())))
				.andExpect(jsonPath("$.totalTime", is(testResult.getTotalTime().intValue())))
				.andExpect(jsonPath("$.totalMiles", is(testResult.getTotalMiles())))
				.andExpect(jsonPath("$.totalCalories", is(testResult.getTotalCalories())))
				.andExpect(jsonPath("$.milesToDate", is(testResult.getMilesToDate().intValue())))
				.andExpect(jsonPath("$.daysExercised", is(testResult.getDaysExercised())))
				.andExpect(jsonPath("$.dateRecorded", is(testResult.getDateRecorded().toString())))
				.andExpect(jsonPath("$.exerciseType", is(testResult.getExerciseType())));

		// verify that the method was only called once
		verify(fitnessWeekServiceMock, times(1)).getFitnessWeekById(id);
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testGetWeekBySpecificIdNotFound() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		FitnessWeek testResult = testDataResults.get(0);
		Long id = testResult.getId();

		when(fitnessWeekServiceMock.getFitnessWeekById(id))
				.thenThrow(new EntityNotFoundException());
		mockMvc.perform(get(FitnessWeekEndpointConstants.GET_WEEK, id))
				.andExpect(status().isNotFound());

		// verify that the method was only called once
		verify(fitnessWeekServiceMock, times(1)).getFitnessWeekById(id);
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testCreateWeekSuccess() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		FitnessWeek testResult = testDataResults.get(0);
		FitnessWeek testInput = new FitnessWeekBuilder(testResult).withId(null).build();

		when(fitnessWeekServiceMock.createFitnessWeek(Mockito.any(FitnessWeek.class)))
				.thenReturn(testResult);
		mockMvc.perform(post(FitnessWeekEndpointConstants.CREATE_WEEK)
				.contentType(MediaType.APPLICATION_JSON)
				.content(testUtil.convertObjectToJsonBytes(testInput))).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is(testResult.getId().intValue())))
				.andExpect(jsonPath("$.totalTime", is(testResult.getTotalTime().intValue())))
				.andExpect(jsonPath("$.totalMiles", is(testResult.getTotalMiles())))
				.andExpect(jsonPath("$.totalCalories", is(testResult.getTotalCalories())))
				.andExpect(jsonPath("$.milesToDate", is(testResult.getMilesToDate().intValue())))
				.andExpect(jsonPath("$.daysExercised", is(testResult.getDaysExercised())))
				.andExpect(jsonPath("$.dateRecorded", is(testResult.getDateRecorded().toString())))
				.andExpect(jsonPath("$.exerciseType", is(testResult.getExerciseType())));

		// verify that the method was only called once
		ArgumentCaptor<FitnessWeek> captor = ArgumentCaptor.forClass(FitnessWeek.class);
		verify(fitnessWeekServiceMock, times(1)).createFitnessWeek(captor.capture());
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testGetBetweenDatesSuccess() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		when(fitnessWeekServiceMock.getInDateRange(Mockito.any(LocalDate.class),
				Mockito.any(LocalDate.class)))
						.thenReturn(testDataResults);
		mockMvc.perform(get(FitnessWeekEndpointConstants.GET_IN_RANGE)
				.param("startDate", "2020-02-20").param("endDate",
						"2020-03-10"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(testDataResults.size())))
				.andExpect(jsonPath("$[0].id", is(testDataResults.get(0).getId().intValue())));

		// verify that the method was only called once
		verify(fitnessWeekServiceMock, times(1)).getInDateRange(Mockito.any(LocalDate.class),
				Mockito.any(LocalDate.class));
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testGetByExerciseTypesSuccess() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		when(fitnessWeekServiceMock.getByExerciseTypes(Mockito.anyList()))
				.thenReturn(testDataResults);
		mockMvc.perform(
				get(FitnessWeekEndpointConstants.GET_BY_EXERCISE_TYPE).param("exerciseTypes",
						"Cycling,Running"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(testDataResults.size())))
				.andExpect(jsonPath("$[0].id", is(testDataResults.get(0).getId().intValue())));

		// verify that the method was only called once
		verify(fitnessWeekServiceMock, times(1)).getByExerciseTypes(Mockito.anyList());
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testGetByIdsSuccess() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		when(fitnessWeekServiceMock.getByIds(Mockito.anyList())).thenReturn(testDataResults);
		mockMvc.perform(get(FitnessWeekEndpointConstants.GET_BY_IDS).param("ids", "1,2,3"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(testDataResults.size())))
				.andExpect(jsonPath("$[0].id", is(testDataResults.get(0).getId().intValue())));

		// verify that the method was only called once
		verify(fitnessWeekServiceMock, times(1)).getByIds(Mockito.anyList());
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testSumAllSuccess() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		Double totalCalories = 20.0;
		Double totalMiles = 10.0;
		Long totalTime = 120L;
		when(fitnessWeekServiceMock.getAllFitnessWeek()).thenReturn(testDataResults);
		when(fitnessWeekServiceMock.sumTotalCaloriesFor(Mockito.anyList()))
				.thenReturn(totalCalories);
		when(fitnessWeekServiceMock.sumTotalMilesFor(Mockito.anyList())).thenReturn(totalMiles);
		when(fitnessWeekServiceMock.sumTotalTimeFor(Mockito.anyList())).thenReturn(totalTime);

		mockMvc.perform(get(FitnessWeekEndpointConstants.SUM_ALL)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.totalCalories", is(totalCalories)))
				.andExpect(jsonPath("$.totalMiles", is(totalMiles)))
				.andExpect(jsonPath("$.totalTime", is(totalTime.intValue())));

		// verify that the method was only called once
		verify(fitnessWeekServiceMock, times(1)).getAllFitnessWeek();
		verify(fitnessWeekServiceMock, times(1)).sumTotalCaloriesFor(Mockito.anyList());
		verify(fitnessWeekServiceMock, times(1)).sumTotalMilesFor(Mockito.anyList());
		verify(fitnessWeekServiceMock, times(1)).sumTotalTimeFor(Mockito.anyList());
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testSumInRangeSuccess() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		Double totalCalories = 20.0;
		Double totalMiles = 10.0;
		Long totalTime = 120L;
		when(fitnessWeekServiceMock.sumTotalCaloriesFor(Mockito.anyList()))
				.thenReturn(totalCalories);
		when(fitnessWeekServiceMock.sumTotalMilesFor(Mockito.anyList())).thenReturn(totalMiles);
		when(fitnessWeekServiceMock.sumTotalTimeFor(Mockito.anyList())).thenReturn(totalTime);
		when(fitnessWeekServiceMock.getInDateRange(Mockito.any(LocalDate.class),
				Mockito.any(LocalDate.class)))
						.thenReturn(testDataResults);
		mockMvc.perform(get(FitnessWeekEndpointConstants.SUM_IN_RANGE)
				.param("startDate", "2020-02-20").param("endDate",
						"2020-03-10"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.totalCalories", is(totalCalories)))
				.andExpect(jsonPath("$.totalMiles", is(totalMiles)))
				.andExpect(jsonPath("$.totalTime", is(totalTime.intValue())));

		// verify that the method was only called once
		verify(fitnessWeekServiceMock, times(1)).getInDateRange(Mockito.any(LocalDate.class),
				Mockito.any(LocalDate.class));
		verify(fitnessWeekServiceMock, times(1)).sumTotalCaloriesFor(Mockito.anyList());
		verify(fitnessWeekServiceMock, times(1)).sumTotalMilesFor(Mockito.anyList());
		verify(fitnessWeekServiceMock, times(1)).sumTotalTimeFor(Mockito.anyList());
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testSumByIdsSuccess() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		Double totalCalories = 20.0;
		Double totalMiles = 10.0;
		Long totalTime = 120L;
		when(fitnessWeekServiceMock.sumTotalCaloriesFor(Mockito.anyList()))
				.thenReturn(totalCalories);
		when(fitnessWeekServiceMock.sumTotalMilesFor(Mockito.anyList())).thenReturn(totalMiles);
		when(fitnessWeekServiceMock.sumTotalTimeFor(Mockito.anyList())).thenReturn(totalTime);
		when(fitnessWeekServiceMock.getByIds(Mockito.anyList())).thenReturn(testDataResults);

		mockMvc.perform(get(FitnessWeekEndpointConstants.SUM_BY_IDS).param("ids", "1,2,3"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.totalCalories", is(totalCalories)))
				.andExpect(jsonPath("$.totalMiles", is(totalMiles)))
				.andExpect(jsonPath("$.totalTime", is(totalTime.intValue())));

		// verify that the method was only called once
		verify(fitnessWeekServiceMock, times(1)).getByIds(Mockito.anyList());
		verify(fitnessWeekServiceMock, times(1)).sumTotalCaloriesFor(Mockito.anyList());
		verify(fitnessWeekServiceMock, times(1)).sumTotalMilesFor(Mockito.anyList());
		verify(fitnessWeekServiceMock, times(1)).sumTotalTimeFor(Mockito.anyList());
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testSumByExerciseTypesSuccess() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		Double totalCalories = 20.0;
		Double totalMiles = 10.0;
		Long totalTime = 120L;
		when(fitnessWeekServiceMock.sumTotalCaloriesFor(Mockito.anyList()))
				.thenReturn(totalCalories);
		when(fitnessWeekServiceMock.sumTotalMilesFor(Mockito.anyList())).thenReturn(totalMiles);
		when(fitnessWeekServiceMock.sumTotalTimeFor(Mockito.anyList())).thenReturn(totalTime);
		when(fitnessWeekServiceMock.getByExerciseTypes(Mockito.anyList()))
				.thenReturn(testDataResults);

		mockMvc.perform(
				get(FitnessWeekEndpointConstants.SUM_BY_EXERCISE_TYPES).param("exerciseTypes",
						"Cycling,Running"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.totalCalories", is(totalCalories)))
				.andExpect(jsonPath("$.totalMiles", is(totalMiles)))
				.andExpect(jsonPath("$.totalTime", is(totalTime.intValue())));

		// verify that the method was only called once
		verify(fitnessWeekServiceMock, times(1)).getByExerciseTypes(Mockito.anyList());
		verify(fitnessWeekServiceMock, times(1)).sumTotalCaloriesFor(Mockito.anyList());
		verify(fitnessWeekServiceMock, times(1)).sumTotalMilesFor(Mockito.anyList());
		verify(fitnessWeekServiceMock, times(1)).sumTotalTimeFor(Mockito.anyList());
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testSumByExerciseTypesNotFound() throws Exception {
		List<FitnessWeek> emptyList = new ArrayList<>();
		Double totalCalories = 20.0;
		Double totalMiles = 10.0;
		Long totalTime = 120L;
		when(fitnessWeekServiceMock.sumTotalCaloriesFor(Mockito.anyList()))
				.thenReturn(totalCalories);
		when(fitnessWeekServiceMock.sumTotalMilesFor(Mockito.anyList())).thenReturn(totalMiles);
		when(fitnessWeekServiceMock.sumTotalTimeFor(Mockito.anyList())).thenReturn(totalTime);
		when(fitnessWeekServiceMock.getByExerciseTypes(Mockito.anyList())).thenReturn(emptyList);
		mockMvc.perform(get(FitnessWeekEndpointConstants.SUM_BY_EXERCISE_TYPES)
				.param("exerciseTypes", "Cycling,Running")).andExpect(status().isNotFound());
		// verify that the method was only called once
		verify(fitnessWeekServiceMock, times(1)).getByExerciseTypes(Mockito.anyList());
		verify(fitnessWeekServiceMock, times(0)).sumTotalCaloriesFor(Mockito.anyList());
		verify(fitnessWeekServiceMock, times(0)).sumTotalMilesFor(Mockito.anyList());
		verify(fitnessWeekServiceMock, times(0)).sumTotalTimeFor(Mockito.anyList());
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testSumByIdsNotFound() throws Exception {
		List<FitnessWeek> emptyList = new ArrayList<>();
		Double totalCalories = 20.0;
		Double totalMiles = 10.0;
		Long totalTime = 120L;
		when(fitnessWeekServiceMock.sumTotalCaloriesFor(Mockito.anyList()))
				.thenReturn(totalCalories);
		when(fitnessWeekServiceMock.sumTotalMilesFor(Mockito.anyList())).thenReturn(totalMiles);
		when(fitnessWeekServiceMock.sumTotalTimeFor(Mockito.anyList())).thenReturn(totalTime);
		when(fitnessWeekServiceMock.getByIds(Mockito.anyList())).thenReturn(emptyList);
		mockMvc.perform(get(FitnessWeekEndpointConstants.SUM_BY_IDS)
				.param("ids", "1,2")).andExpect(status().isNotFound());
		// verify that the method was only called once
		verify(fitnessWeekServiceMock, times(1)).getByIds(Mockito.anyList());
		verify(fitnessWeekServiceMock, times(0)).sumTotalCaloriesFor(Mockito.anyList());
		verify(fitnessWeekServiceMock, times(0)).sumTotalMilesFor(Mockito.anyList());
		verify(fitnessWeekServiceMock, times(0)).sumTotalTimeFor(Mockito.anyList());
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testSumInRangeNotFound() throws Exception {
		List<FitnessWeek> emptyList = new ArrayList<>();
		Double totalCalories = 20.0;
		Double totalMiles = 10.0;
		Long totalTime = 120L;
		when(fitnessWeekServiceMock.sumTotalCaloriesFor(Mockito.anyList()))
				.thenReturn(totalCalories);
		when(fitnessWeekServiceMock.sumTotalMilesFor(Mockito.anyList())).thenReturn(totalMiles);
		when(fitnessWeekServiceMock.sumTotalTimeFor(Mockito.anyList())).thenReturn(totalTime);
		when(fitnessWeekServiceMock.getInDateRange(Mockito.any(LocalDate.class),
				Mockito.any(LocalDate.class)))
						.thenReturn(emptyList);
		mockMvc.perform(get(FitnessWeekEndpointConstants.SUM_IN_RANGE)
				.param("startDate", "2020-02-20").param("endDate",
						"2020-03-10"))
				.andExpect(status().isNotFound());
		// verify that the method was only called once
		verify(fitnessWeekServiceMock, times(1)).getInDateRange(Mockito.any(LocalDate.class),
				Mockito.any(LocalDate.class));
		verify(fitnessWeekServiceMock, times(0)).sumTotalCaloriesFor(Mockito.anyList());
		verify(fitnessWeekServiceMock, times(0)).sumTotalMilesFor(Mockito.anyList());
		verify(fitnessWeekServiceMock, times(0)).sumTotalTimeFor(Mockito.anyList());
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testSumAllWeeksNotFound() throws Exception {
		List<FitnessWeek> emptyList = new ArrayList<>();
		Double totalCalories = 20.0;
		Double totalMiles = 10.0;
		Long totalTime = 120L;
		when(fitnessWeekServiceMock.sumTotalCaloriesFor(Mockito.anyList()))
				.thenReturn(totalCalories);
		when(fitnessWeekServiceMock.sumTotalMilesFor(Mockito.anyList())).thenReturn(totalMiles);
		when(fitnessWeekServiceMock.sumTotalTimeFor(Mockito.anyList())).thenReturn(totalTime);
		when(fitnessWeekServiceMock.getAllFitnessWeek()).thenReturn(emptyList);
		mockMvc.perform(get(FitnessWeekEndpointConstants.SUM_ALL)).andExpect(status().isNotFound());
		// verify that the method was only called once
		verify(fitnessWeekServiceMock, times(1)).getAllFitnessWeek();
		verify(fitnessWeekServiceMock, times(0)).sumTotalCaloriesFor(Mockito.anyList());
		verify(fitnessWeekServiceMock, times(0)).sumTotalMilesFor(Mockito.anyList());
		verify(fitnessWeekServiceMock, times(0)).sumTotalTimeFor(Mockito.anyList());
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testSumMonthly() throws Exception {
		Double totalCalories = 20.0;
		Double totalMiles = 10.0;
		Long totalTime = 120L;
		String startDateStr = "2020-01-01";
		String endDateStr = "2020-01-15";
		List<FitnessWeekSum> sums = new ArrayList<>();
		sums.add(new FitnessWeekSum(totalMiles, totalCalories, totalTime));
		sums.get(0).setEndDate(LocalDate.parse(endDateStr));
		sums.get(0).setStartDate(LocalDate.parse(startDateStr));
		when(fitnessWeekServiceMock.sumMonthlyForDateRange(Mockito.any(), Mockito.any()))
				.thenReturn(sums);

		mockMvc.perform(get(FitnessWeekEndpointConstants.SUM_MONTHLY)
				.param("startDate", startDateStr).param("endDate", endDateStr))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(sums.size())))
				.andExpect(jsonPath("$[0].totalCalories", is(totalCalories)))
				.andExpect(jsonPath("$[0].totalMiles", is(totalMiles)))
				.andExpect(jsonPath("$[0].totalTime", is(totalTime.intValue())))
				.andExpect(jsonPath("$[0].startDate", is(startDateStr)))
				.andExpect(jsonPath("$[0].endDate", is(endDateStr)));
		verify(fitnessWeekServiceMock, times(1)).sumMonthlyForDateRange(Mockito.any(),
				Mockito.any());
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testSumMonthlyNotFound() throws Exception {
		List<FitnessWeekSum> emptyList = new ArrayList<>();
		when(fitnessWeekServiceMock.sumMonthlyForDateRange(Mockito.any(), Mockito.any()))
				.thenReturn(emptyList);
		mockMvc.perform(get(FitnessWeekEndpointConstants.SUM_MONTHLY)
				.param("startDate", "2020-01-01").param("endDate", "2020-03-01"))
				.andExpect(status().isNotFound());
		verify(fitnessWeekServiceMock, times(1)).sumMonthlyForDateRange(Mockito.any(),
				Mockito.any());
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testUpdateFitnessWeek_Success() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		FitnessWeek weekToUpdate = testDataResults.get(0);
		when(fitnessWeekServiceMock.updateFitnessWeek(Mockito.any())).thenReturn(weekToUpdate);
		mockMvc.perform(put(FitnessWeekEndpointConstants.UPDATE_WEEK)
				.contentType(MediaType.APPLICATION_JSON)
				.content(testUtil.convertObjectToJsonBytes(weekToUpdate)))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is(weekToUpdate.getId().intValue())))
				.andExpect(jsonPath("$.totalTime", is(weekToUpdate.getTotalTime().intValue())))
				.andExpect(jsonPath("$.totalMiles", is(weekToUpdate.getTotalMiles())))
				.andExpect(jsonPath("$.totalCalories", is(weekToUpdate.getTotalCalories())))
				.andExpect(jsonPath("$.milesToDate", is(weekToUpdate.getMilesToDate().intValue())))
				.andExpect(jsonPath("$.daysExercised", is(weekToUpdate.getDaysExercised())))
				.andExpect(
						jsonPath("$.dateRecorded", is(weekToUpdate.getDateRecorded().toString())))
				.andExpect(jsonPath("$.exerciseType", is(weekToUpdate.getExerciseType())));
		verify(fitnessWeekServiceMock, times(1)).updateFitnessWeek(Mockito.any());
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testCreateFitnessWeeks_Success() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		FitnessWeek testResult = testDataResults.get(0);
		when(fitnessWeekServiceMock.createFitnessWeeks(Mockito.anyList()))
				.thenReturn(testDataResults);
		mockMvc.perform(post(FitnessWeekEndpointConstants.CREATE_WEEKS)
				.contentType(MediaType.APPLICATION_JSON)
				.content(testUtil.convertObjectToJsonBytes(testDataResults)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(testDataResults.size())))
				.andExpect(jsonPath("$[0].id", is(testResult.getId().intValue())))
				.andExpect(jsonPath("$[0].totalTime", is(testResult.getTotalTime().intValue())))
				.andExpect(jsonPath("$[0].totalMiles", is(testResult.getTotalMiles())))
				.andExpect(jsonPath("$[0].totalCalories", is(testResult.getTotalCalories())))
				.andExpect(jsonPath("$[0].milesToDate", is(testResult.getMilesToDate().intValue())))
				.andExpect(jsonPath("$[0].daysExercised", is(testResult.getDaysExercised())))
				.andExpect(
						jsonPath("$[0].dateRecorded", is(testResult.getDateRecorded().toString())))
				.andExpect(jsonPath("$[0].exerciseType", is(testResult.getExerciseType())));

		verify(fitnessWeekServiceMock, times(1)).createFitnessWeeks(Mockito.anyList());
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testDeleteWeekById_Success() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		FitnessWeek testResult = testDataResults.get(0);
		Long id = testResult.getId();

		mockMvc.perform(delete(FitnessWeekEndpointConstants.GET_WEEK, id))
				.andExpect(status().isOk());

		verify(fitnessWeekServiceMock, times(1)).deleteFitnessWeekById(Mockito.anyLong());
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testDeleteWeekById_NotFound() throws Exception {
		Long idNotExists = 0L;
		Mockito.doThrow(EntityNotFoundException.class).when(fitnessWeekServiceMock)
				.deleteFitnessWeekById(Mockito.anyLong());
		mockMvc.perform(delete(FitnessWeekEndpointConstants.GET_WEEK, idNotExists))
				.andExpect(status().isNotFound());

		verify(fitnessWeekServiceMock, times(1)).deleteFitnessWeekById(Mockito.anyLong());
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}
}
