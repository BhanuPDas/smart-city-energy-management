package com.fhdo.energy_analysis_service.controller;

import com.fhdo.energy_analysis_service.model.Building;
import com.fhdo.energy_analysis_service.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analytics/buildings")
public class BuildingController {

    private final BuildingService buildingService;

    @Autowired
    public BuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @GetMapping("/by-email")
    public ResponseEntity<Building> getByEmail(@RequestParam String email) {
        return buildingService.getBuildingByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-zipcode")
    public ResponseEntity<List<Building>> getByZipCode(@RequestParam Integer zipCode) {
        return ResponseEntity.ok(buildingService.getBuildingsByZipCode(zipCode));
    }

    @GetMapping("/by-city")
    public ResponseEntity<List<Building>> getByCity(@RequestParam String city) {
        return ResponseEntity.ok(buildingService.getBuildingsByCity(city));
    }
}
