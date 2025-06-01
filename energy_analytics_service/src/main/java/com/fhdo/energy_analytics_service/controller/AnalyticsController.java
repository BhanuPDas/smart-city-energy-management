package com.fhdo.energy_analytics_service.controller;

import com.fhdo.energy_analytics_service.domain.entity.BuildingEntity;
import com.fhdo.energy_analytics_service.service.AnalyticsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/analytics")
public class AnalyticsController {

	@Autowired
	private AnalyticsService service;

	@GetMapping("/by-email")
	public ResponseEntity<BuildingEntity> getByEmail(@RequestParam String email) {
		return service.getBuildingByEmail(email).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/by-zipcode")
	public ResponseEntity<List<BuildingEntity>> getByZipCode(@RequestParam Integer zipCode) {
		return ResponseEntity.ok(service.getBuildingsByZipCode(zipCode));
	}

	@GetMapping("/by-city")
	public ResponseEntity<List<BuildingEntity>> getByCity(@RequestParam String city) {
		return ResponseEntity.ok(service.getBuildingsByCity(city));
	}
}
