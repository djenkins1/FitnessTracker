package com.djenkins.fitness.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.djenkins.fitness.domain.FitnessWeek;

public interface FitnessWeekRepository extends CrudRepository<FitnessWeek, Long> {
	List<FitnessWeek> findAll();

	Optional<FitnessWeek> findById( Long id );

	List<FitnessWeek> findByExerciseType(String exerciseType);
}
