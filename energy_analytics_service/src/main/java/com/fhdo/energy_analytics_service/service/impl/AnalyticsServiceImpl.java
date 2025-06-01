package com.fhdo.energy_analytics_service.service.impl;

import com.fhdo.energy_analytics_service.domain.entity.BuildingEntity;
import com.fhdo.energy_analytics_service.repository.BuildingRepository;
import com.fhdo.energy_analytics_service.service.AnalyticsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

	private final Logger logger = LoggerFactory.getLogger(AnalyticsServiceImpl.class);
	@Autowired
	private BuildingRepository repo;

	public Optional<BuildingEntity> getBuildingByEmail(String email) {
		try {
			return repo.findByOwnerEmail(email);
		} catch (Exception e) {
			logger.error("Exception Occured while fetching consumption details for the building:" + e.getMessage());
		}
		return null;
	}

	public List<BuildingEntity> getBuildingsByZipCode(Integer zipCode) {
		try {
			return repo.findByZipCode(zipCode);
		} catch (Exception e) {
			logger.error("Exception Occured while fetching consumption details for the neighborhood:" + e.getMessage());
		}
		return null;
	}

	public List<BuildingEntity> getBuildingsByCity(String city) {
		try {
			return repo.findByCity(city);
		} catch (Exception e) {
			logger.error("Exception Occured while fetching consumption details for the city:" + e.getMessage());
		}
		return null;
	}
}
