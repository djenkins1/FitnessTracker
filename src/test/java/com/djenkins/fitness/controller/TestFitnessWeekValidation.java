package com.djenkins.fitness.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import java.util.List;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.djenkins.fitness.domain.FitnessWeek;
import com.djenkins.fitness.factory.FitnessWeekBuilder;
import com.djenkins.fitness.service.FitnessWeekService;
import com.djenkins.fitness.util.FitnessWeekTestData;
import com.djenkins.fitness.util.TestUtil;

import static org.mockito.Mockito.*;

@SpringBootTest
@WebAppConfiguration
public class TestFitnessWeekValidation {
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
	public void testCreateWeek_Fail_dateRecordedInFuture() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		LocalDate futureDate = LocalDate.now().plusDays(1L);
		FitnessWeek weekCloned = new FitnessWeekBuilder(testDataResults.get(0))
				.withDateRecorded(futureDate).build();
		performCreateWeek_ExpectBadRequest(weekCloned);
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testCreateWeek_Fail_dateRecordedNull() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		FitnessWeek weekCloned = new FitnessWeekBuilder(testDataResults.get(0))
				.withDateRecorded(null).build();
		performCreateWeek_ExpectBadRequest(weekCloned);
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testCreateWeek_Fail_exerciseTypeBlank() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		String badExerciseType = "";
		FitnessWeek weekCloned = new FitnessWeekBuilder(testDataResults.get(0))
				.withExerciseType(badExerciseType).build();
		performCreateWeek_ExpectBadRequest(weekCloned);
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testCreateWeek_Fail_exerciseTypeTooLarge() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		String badExerciseType = "ABCDEFGHIJ" + "LMNOPQRSTU" + "V";
		assertEquals(21, badExerciseType.length());
		FitnessWeek weekCloned = new FitnessWeekBuilder(testDataResults.get(0))
				.withExerciseType(badExerciseType).build();
		performCreateWeek_ExpectBadRequest(weekCloned);
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testCreateWeek_Fail_daysExercisedBlank() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		String badDaysExercised = "";
		FitnessWeek weekCloned = new FitnessWeekBuilder(testDataResults.get(0))
				.withDaysExercised(badDaysExercised).build();
		performCreateWeek_ExpectBadRequest(weekCloned);
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testCreateWeek_Fail_daysExercisedTooLarge() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		String badDaysExercised = "ABCDEFGHIJ" + "LMNOPQRSTU" + "V";
		assertEquals(21, badDaysExercised.length());
		FitnessWeek weekCloned = new FitnessWeekBuilder(testDataResults.get(0))
				.withDaysExercised(badDaysExercised).build();
		performCreateWeek_ExpectBadRequest(weekCloned);
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testCreateWeek_Fail_milesToDateNegative() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		Long badMilesToDate = -1L;
		FitnessWeek weekCloned = new FitnessWeekBuilder(testDataResults.get(0))
				.withMilesToDate(badMilesToDate).build();
		performCreateWeek_ExpectBadRequest(weekCloned);
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testCreateWeek_Fail_milesToDateNull() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		Long badMilesToDate = null;
		FitnessWeek weekCloned = new FitnessWeekBuilder(testDataResults.get(0))
				.withMilesToDate(badMilesToDate).build();
		performCreateWeek_ExpectBadRequest(weekCloned);
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testCreateWeek_Fail_totalTimeNegative() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		Long badTotalTime = -1L;
		FitnessWeek weekCloned = new FitnessWeekBuilder(testDataResults.get(0))
				.withTotalTime(badTotalTime).build();
		performCreateWeek_ExpectBadRequest(weekCloned);
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testCreateWeek_Fail_totalTimeNull() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		Long badTotalTime = null;
		FitnessWeek weekCloned = new FitnessWeekBuilder(testDataResults.get(0))
				.withTotalTime(badTotalTime).build();
		performCreateWeek_ExpectBadRequest(weekCloned);
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testCreateWeek_Fail_totalMilesNegative() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		Double badTotalMiles = -1.0;
		FitnessWeek weekCloned = new FitnessWeekBuilder(testDataResults.get(0))
				.withTotalMiles(badTotalMiles).build();
		performCreateWeek_ExpectBadRequest(weekCloned);
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testCreateWeek_Fail_totalMilesNull() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		Double badTotalMiles = null;
		FitnessWeek weekCloned = new FitnessWeekBuilder(testDataResults.get(0))
				.withTotalMiles(badTotalMiles).build();
		performCreateWeek_ExpectBadRequest(weekCloned);
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testCreateWeek_Fail_totalCaloriesNegative() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		Double badTotalCalories = -1.0;
		FitnessWeek weekCloned = new FitnessWeekBuilder(testDataResults.get(0))
				.withTotalCalories(badTotalCalories).build();
		performCreateWeek_ExpectBadRequest(weekCloned);
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testCreateWeek_Fail_totalCaloriesNull() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		Double badTotalCalories = null;
		FitnessWeek weekCloned = new FitnessWeekBuilder(testDataResults.get(0))
				.withTotalCalories(badTotalCalories).build();
		performCreateWeek_ExpectBadRequest(weekCloned);
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	private void performCreateWeek_ExpectBadRequest(FitnessWeek week) throws Exception {
		mockMvc.perform(post(FitnessWeekEndpointConstants.CREATE_WEEK)
				.contentType(MediaType.APPLICATION_JSON)
				.content(testUtil.convertObjectToJsonBytes(week)))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testUpdateWeek_Fail_dateRecordedInFuture() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		LocalDate futureDate = LocalDate.now().plusDays(1L);
		FitnessWeek weekCloned = new FitnessWeekBuilder(testDataResults.get(0))
				.withDateRecorded(futureDate).build();
		performUpdateWeek_ExpectBadRequest(weekCloned);
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testUpdateWeek_Fail_dateRecordedNull() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		FitnessWeek weekCloned = new FitnessWeekBuilder(testDataResults.get(0))
				.withDateRecorded(null).build();
		performUpdateWeek_ExpectBadRequest(weekCloned);
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testUpdateWeek_Fail_exerciseTypeBlank() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		String badExerciseType = "";
		FitnessWeek weekCloned = new FitnessWeekBuilder(testDataResults.get(0))
				.withExerciseType(badExerciseType).build();
		performUpdateWeek_ExpectBadRequest(weekCloned);
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testUpdateWeek_Fail_exerciseTypeTooLarge() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		String badExerciseType = "ABCDEFGHIJ" + "LMNOPQRSTU" + "V";
		assertEquals(21, badExerciseType.length());
		FitnessWeek weekCloned = new FitnessWeekBuilder(testDataResults.get(0))
				.withExerciseType(badExerciseType).build();
		performUpdateWeek_ExpectBadRequest(weekCloned);
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testUpdateWeek_Fail_daysExercisedBlank() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		String badDaysExercised = "";
		FitnessWeek weekCloned = new FitnessWeekBuilder(testDataResults.get(0))
				.withDaysExercised(badDaysExercised).build();
		performUpdateWeek_ExpectBadRequest(weekCloned);
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testUpdateWeek_Fail_daysExercisedTooLarge() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		String badDaysExercised = "ABCDEFGHIJ" + "LMNOPQRSTU" + "V";
		assertEquals(21, badDaysExercised.length());
		FitnessWeek weekCloned = new FitnessWeekBuilder(testDataResults.get(0))
				.withDaysExercised(badDaysExercised).build();
		performUpdateWeek_ExpectBadRequest(weekCloned);
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testUpdateWeek_Fail_milesToDateNegative() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		Long badMilesToDate = -1L;
		FitnessWeek weekCloned = new FitnessWeekBuilder(testDataResults.get(0))
				.withMilesToDate(badMilesToDate).build();
		performUpdateWeek_ExpectBadRequest(weekCloned);
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testUpdateWeek_Fail_milesToDateNull() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		Long badMilesToDate = null;
		FitnessWeek weekCloned = new FitnessWeekBuilder(testDataResults.get(0))
				.withMilesToDate(badMilesToDate).build();
		performUpdateWeek_ExpectBadRequest(weekCloned);
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testUpdateWeek_Fail_totalTimeNegative() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		Long badTotalTime = -1L;
		FitnessWeek weekCloned = new FitnessWeekBuilder(testDataResults.get(0))
				.withTotalTime(badTotalTime).build();
		performUpdateWeek_ExpectBadRequest(weekCloned);
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testUpdateWeek_Fail_totalTimeNull() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		Long badTotalTime = null;
		FitnessWeek weekCloned = new FitnessWeekBuilder(testDataResults.get(0))
				.withTotalTime(badTotalTime).build();
		performUpdateWeek_ExpectBadRequest(weekCloned);
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testUpdateWeek_Fail_totalMilesNegative() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		Double badTotalMiles = -1.0;
		FitnessWeek weekCloned = new FitnessWeekBuilder(testDataResults.get(0))
				.withTotalMiles(badTotalMiles).build();
		performUpdateWeek_ExpectBadRequest(weekCloned);
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testUpdateWeek_Fail_totalMilesNull() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		Double badTotalMiles = null;
		FitnessWeek weekCloned = new FitnessWeekBuilder(testDataResults.get(0))
				.withTotalMiles(badTotalMiles).build();
		performUpdateWeek_ExpectBadRequest(weekCloned);
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testUpdateWeek_Fail_totalCaloriesNegative() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		Double badTotalCalories = -1.0;
		FitnessWeek weekCloned = new FitnessWeekBuilder(testDataResults.get(0))
				.withTotalCalories(badTotalCalories).build();
		performUpdateWeek_ExpectBadRequest(weekCloned);
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	@Test
	public void testUpdateWeek_Fail_totalCaloriesNull() throws Exception {
		List<FitnessWeek> testDataResults = testData.getAllData();
		Double badTotalCalories = null;
		FitnessWeek weekCloned = new FitnessWeekBuilder(testDataResults.get(0))
				.withTotalCalories(badTotalCalories).build();
		performUpdateWeek_ExpectBadRequest(weekCloned);
		verifyNoMoreInteractions(fitnessWeekServiceMock);
	}

	private void performUpdateWeek_ExpectBadRequest(FitnessWeek week) throws Exception {
		mockMvc.perform(put(FitnessWeekEndpointConstants.UPDATE_WEEK)
				.contentType(MediaType.APPLICATION_JSON)
				.content(testUtil.convertObjectToJsonBytes(week)))
				.andExpect(status().isBadRequest());
	}

	// TODO: TEST CREATE WEEKS WITH INVALID INPUTS
	@Test
	public void testCreateWeeks_Fail_totalCaloriesNull() {
		//TODO:
		fail("Not yet implemented");
	}

	// TODO: TEST GET BETWEEN DATES WITH INVALID INPUTS
}
