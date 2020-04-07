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
}
