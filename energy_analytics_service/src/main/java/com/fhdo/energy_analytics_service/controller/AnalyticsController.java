package com.fhdo.energy_analytics_service.controller;

import com.fhdo.energy_analytics_service.domain.entity.BuildingEntity;
import com.fhdo.energy_analytics_service.service.impl.AnalyticsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analytics/buildings")
public class AnalyticsController {

    private final AnalyticsServiceImpl buildingService;

    @Autowired
    public AnalyticsController(AnalyticsServiceImpl buildingService) {
        this.buildingService = buildingService;
    }

    @GetMapping("/by-email")
    public ResponseEntity<BuildingEntity> getByEmail(@RequestParam String email) {
        return buildingService.getBuildingByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-zipcode")
    public ResponseEntity<List<BuildingEntity>> getByZipCode(@RequestParam Integer zipCode) {
        return ResponseEntity.ok(buildingService.getBuildingsByZipCode(zipCode));
    }

    @GetMapping("/by-city")
    public ResponseEntity<List<BuildingEntity>> getByCity(@RequestParam String city) {
        return ResponseEntity.ok(buildingService.getBuildingsByCity(city));
    }
}
