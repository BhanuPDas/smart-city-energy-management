package com.smartcity.energy_optimization_service.controller;

import com.smartcity.energy_optimization_service.service.OptimizationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class OptimizationController {

	private final Logger logger = LoggerFactory.getLogger(OptimizationController.class);
	@Autowired
	private OptimizationService optimizationService;

	@GetMapping(value = "/optimize")
	public String getRecommendations(@RequestParam String ownerEmail) {
		if (!ownerEmail.isBlank()) {
			logger.info("Optimization Suggestion requested for: " + ownerEmail);
			return optimizationService.generateRecommendation(ownerEmail);
		} else {
			logger.info("Invalid email id received: " + ownerEmail);
			return null;
		}
	}
}
