package com.djenkins.fitness.spec;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.djenkins.fitness.domain.FitnessWeek;
import com.djenkins.fitness.domain.FitnessWeekFilter;

public class FitnessWeekSpec implements Specification<FitnessWeek> {
	private static final long serialVersionUID = 1L;
	private FitnessWeekFilter filterByCriteria;

	public FitnessWeekSpec(FitnessWeekFilter filter) {
		this.filterByCriteria = filter;
	}

	@Override
	public Predicate toPredicate(Root<FitnessWeek> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<>();

		// add condition for id in list of ids if it was provided in filter
		if (filterByCriteria.getIds() != null && !filterByCriteria.getIds().isEmpty()) {
			predicates.add(root.get("id").in(filterByCriteria.getIds()));
		}

		// add condition for dateRecorded between fromDate and toDate if both dates were
		// provided
		if (filterByCriteria.getToDateRecorded() != null && filterByCriteria.getFromDateRecorded() != null) {
			predicates.add(criteriaBuilder.between(root.get("dateRecorded"), filterByCriteria.getFromDateRecorded(),
					filterByCriteria.getToDateRecorded()));
		}

		// add condition for days exercised if list of days exercised was provided in
		// filter
		if (filterByCriteria.getDaysExercised() != null && !filterByCriteria.getDaysExercised().isEmpty()) {
			predicates.add(root.get("daysExercised").in(filterByCriteria.getDaysExercised()));
		}

		// add condition for exercise type if list of exercise types was provided in
		// filter
		if (filterByCriteria.getExerciseTypes() != null && !filterByCriteria.getExerciseTypes().isEmpty()) {
			predicates.add(root.get("exerciseType").in(filterByCriteria.getExerciseTypes()));
		}

		return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
	}

	public FitnessWeekFilter getFilterByCriteria() {
		return filterByCriteria;
	}

	public void setFilterByCriteria(FitnessWeekFilter filterByCriteria) {
		this.filterByCriteria = filterByCriteria;
	}

}
