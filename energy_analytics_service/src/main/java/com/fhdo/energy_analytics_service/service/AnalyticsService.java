package com.fhdo.energy_analytics_service.service;

import java.util.List;
import java.util.Optional;

import com.fhdo.energy_analytics_service.domain.entity.BuildingEntity;

public interface AnalyticsService {
	
	public Optional<BuildingEntity> getBuildingByEmail(String email);
	public List<BuildingEntity> getBuildingsByZipCode(Integer zipCode);
	public List<BuildingEntity> getBuildingsByCity(String city);

}
