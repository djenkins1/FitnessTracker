package com.djenkins.fitness.spec;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.djenkins.fitness.domain.FitnessWeek;
import com.djenkins.fitness.domain.FitnessWeekFilter;
import com.djenkins.fitness.repo.FitnessWeekRepository;

@SpringBootTest
public class TestFitnessWeekSpec {
	@Autowired
	private FitnessWeekRepository fitnessWeekRepo;
	
	public void testFilterByIds() {
		List<Long> listOfIds = new ArrayList<>();
		listOfIds.add( 1L );
		listOfIds.add( 2L );
		FitnessWeekFilter filter = new FitnessWeekFilter();
		filter.setIds( listOfIds );
		
		List<FitnessWeek> results = fitnessWeekRepo.findAll( new FitnessWeekSpec( filter  ) );
		assertEquals( 2 , results.size() ) ;
	}
	
	//TODO: test filter by dates
	
	//TODO: test filter by days exercised
	
	//TODO: test filter by exercise types
}
