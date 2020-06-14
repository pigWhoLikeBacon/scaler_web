package com.scaler.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scaler.entity.Plan;
import com.scaler.repository.PlanRepository;

@Service
public class PlanService {
	@Autowired
	private PlanRepository planRepository;
	
	public List<Plan> findByUserId(int id) {
		return planRepository.findByUserId(id);
	}
}
