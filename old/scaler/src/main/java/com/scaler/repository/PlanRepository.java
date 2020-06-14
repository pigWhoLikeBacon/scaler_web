package com.scaler.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.scaler.entity.Plan;

public interface PlanRepository extends JpaRepository<Plan, Integer> {
	
	@Query("SELECT plan FROM Plan plan WHERE plan.id IN"
			+ " (SELECT dp.id FROM DayPlan dp WHERE dp.day.id = ?1)")
	List<Plan> findByUserId(int id);
}
