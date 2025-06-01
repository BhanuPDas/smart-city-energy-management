package com.fhdo.energy_analytics_service.controller;

import com.fhdo.energy_analytics_service.domain.response.AnalyticsResponse;
import com.fhdo.energy_analytics_service.service.AnalyticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/analytics")
public class AnalyticsController {

	private final Logger logger = LoggerFactory.getLogger(AnalyticsController.class);
	@Autowired
	private AnalyticsService service;

	@GetMapping("/email")
	public ResponseEntity<List<AnalyticsResponse>> getByEmail(@RequestParam("email") String email,
			@RequestParam("startDate") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate) {
		logger.info("Generating Analytics report for:" + email);
		return ResponseEntity.ok(service.getBuildingByEmail(email, startDate));
	}

	@GetMapping("/zipcode")
	public ResponseEntity<List<AnalyticsResponse>> getByZipCode(@RequestParam("zipCode") Integer zipCode,
			@RequestParam("startDate") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate) {
		logger.info("Generating Analytics report for the neighborhood:" + zipCode);
		return ResponseEntity.ok(service.getBuildingsByZipCode(zipCode, startDate));
	}

	@GetMapping("/city")
	public ResponseEntity<List<AnalyticsResponse>> getByCity(@RequestParam("city") String city,
			@RequestParam("startDate") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate) {
		logger.info("Generating Analytics report for the city:" + city);
		return ResponseEntity.ok(service.getBuildingsByCity(city, startDate));
	}
}
