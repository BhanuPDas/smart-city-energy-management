package com.fhdo.energy_analysis_service.service;

import com.fhdo.energy_analysis_service.model.Building;
import com.fhdo.energy_analysis_service.repository.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BuildingService {

    private final BuildingRepository buildingRepository;

    @Autowired
    public BuildingService(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    public Optional<Building> getBuildingByEmail(String email) {
        return buildingRepository.findByOwnerEmail(email);
    }

    public List<Building> getBuildingsByZipCode(Integer zipCode) {
        return buildingRepository.findByZipCode(zipCode);
    }

    public List<Building> getBuildingsByCity(String city) {
        return buildingRepository.findByCity(city);
    }
}
