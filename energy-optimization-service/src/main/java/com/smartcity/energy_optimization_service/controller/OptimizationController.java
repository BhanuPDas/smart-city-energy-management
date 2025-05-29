package com.smartcity.energy_optimization_service.controller;

import com.smartcity.energy_optimization_service.service.OptimizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/optimize")
public class OptimizationController {

    @Autowired
    private OptimizationService optimizationService;

    @GetMapping
    public String getRecommendations(@RequestParam String ownerEmail) {
        return optimizationService.generateRecommendation(ownerEmail);
    }
}
