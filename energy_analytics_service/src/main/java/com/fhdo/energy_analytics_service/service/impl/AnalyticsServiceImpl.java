package com.fhdo.energy_analytics_service.service.impl;

import com.fhdo.energy_analytics_service.domain.entity.BuildingEntity;
import com.fhdo.energy_analytics_service.repository.BuildingRepository;
import com.fhdo.energy_analytics_service.service.AnalyticsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

	@Autowired
	private BuildingRepository repo;

	public Optional<BuildingEntity> getBuildingByEmail(String email) {
		return repo.findByOwnerEmail(email);
	}

	public List<BuildingEntity> getBuildingsByZipCode(Integer zipCode) {
		return repo.findByZipCode(zipCode);
	}

	public List<BuildingEntity> getBuildingsByCity(String city) {
		return repo.findByCity(city);
	}
}
