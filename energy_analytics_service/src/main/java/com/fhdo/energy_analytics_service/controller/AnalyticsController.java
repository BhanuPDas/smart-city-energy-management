package com.fhdo.energy_analytics_service.controller;

import com.fhdo.energy_analytics_service.domain.entity.BuildingEntity;
import com.fhdo.energy_analytics_service.service.AnalyticsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/analytics")
public class AnalyticsController {

	private final Logger logger = LoggerFactory.getLogger(AnalyticsController.class);
	@Autowired
	private AnalyticsService service;

	@GetMapping("/by-email")
	public ResponseEntity<BuildingEntity> getByEmail(@RequestParam String email) {
		logger.info("Generating Analytics report for:" + email);
		return service.getBuildingByEmail(email).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/by-zipcode")
	public ResponseEntity<List<BuildingEntity>> getByZipCode(@RequestParam Integer zipCode) {
		logger.info("Generating Analytics report for the neighborhood:" + zipCode);
		return ResponseEntity.ok(service.getBuildingsByZipCode(zipCode));
	}

	@GetMapping("/by-city")
	public ResponseEntity<List<BuildingEntity>> getByCity(@RequestParam String city) {
		logger.info("Generating Analytics report for the city:" + city);
		return ResponseEntity.ok(service.getBuildingsByCity(city));
	}
}
