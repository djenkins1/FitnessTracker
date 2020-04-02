package com.djenkins.fitness;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.djenkins.fitness.domain.FitnessWeek;
import com.djenkins.fitness.service.FitnessWeekService;

@SpringBootTest
class FitnessApplicationTests {

	@Autowired
	FitnessWeekService service;

	@Test
	void contextLoads() {
	}

	@Test
	void testInsertData() throws ParseException {
		FitnessWeek fitnessWeek;
		fitnessWeek = new FitnessWeek();
		fitnessWeek.setDateRecorded(new Date(new SimpleDateFormat("M/d/yyyy").parse("2/28/2020").getTime()));
		fitnessWeek.setExerciseType("Cycling");
		fitnessWeek.setCreatedTs(Timestamp.from(Instant.now()));
		fitnessWeek.setDaysExercised("W/T/F");
		fitnessWeek.setMilesToDate(2622L);
		fitnessWeek.setTotalCalories(284.0 + 999.9);
		fitnessWeek.setTotalMiles(53.1);
		fitnessWeek.setTotalTime(180L);
		fitnessWeek = service.createFitnessWeek(fitnessWeek);
		assertEquals(6, fitnessWeek.getId());
	}
	
	@Test
	void testDateParse() throws ParseException {
		Date date = new Date(new SimpleDateFormat("M/d/yyyy").parse("2/28/2020").getTime());
		assertEquals( "2020-02-28" , date.toString() );
	}
}
