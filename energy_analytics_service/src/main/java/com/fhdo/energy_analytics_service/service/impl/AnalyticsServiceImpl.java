package com.fhdo.energy_analytics_service.service.impl;

import com.fhdo.energy_analytics_service.domain.entity.BuildingEntity;
import com.fhdo.energy_analytics_service.repository.BuildingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnalyticsServiceImpl {

    private final BuildingRepository buildingRepository;

    @Autowired
    public AnalyticsServiceImpl(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    public Optional<BuildingEntity> getBuildingByEmail(String email) {
        return buildingRepository.findByOwnerEmail(email);
    }

    public List<BuildingEntity> getBuildingsByZipCode(Integer zipCode) {
        return buildingRepository.findByZipCode(zipCode);
    }

    public List<BuildingEntity> getBuildingsByCity(String city) {
        return buildingRepository.findByCity(city);
    }
}
