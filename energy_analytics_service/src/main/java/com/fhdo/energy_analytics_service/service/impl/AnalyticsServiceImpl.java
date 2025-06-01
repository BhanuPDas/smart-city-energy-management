package com.fhdo.energy_analytics_service.service.impl;

import com.fhdo.energy_analytics_service.domain.entity.BuildingEntity;
import com.fhdo.energy_analytics_service.domain.entity.EnergySourceEntity;
import com.fhdo.energy_analytics_service.domain.response.AnalyticsResponse;
import com.fhdo.energy_analytics_service.repository.BuildingRepository;
import com.fhdo.energy_analytics_service.repository.EnergySourceRepository;
import com.fhdo.energy_analytics_service.service.AnalyticsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

	private final Logger logger = LoggerFactory.getLogger(AnalyticsServiceImpl.class);
	@Autowired
	private BuildingRepository brepo;

	@Autowired
	private EnergySourceRepository erepo;

	public List<AnalyticsResponse> getBuildingByEmail(String email, LocalDate startDate) {
		try {
			Optional<BuildingEntity> building = brepo.findByOwnerEmail(email);
			if (building != null && !building.isEmpty()) {
				Long buildingId = building.get().getId();
				List<EnergySourceEntity> energySources = erepo.findByBuildingIdsAndStartDate(buildingId, startDate);
				return getAnalyticsList(energySources);
			}
			return null;
		} catch (Exception e) {
			logger.error("Exception Occured while fetching consumption details for the building:" + e.getMessage());
		}
		return null;
	}

	public List<AnalyticsResponse> getBuildingsByZipCode(Integer zipCode, LocalDate startDate) {
		try {
			List<BuildingEntity> building = brepo.findByZipCode(zipCode);
			if (building != null && !building.isEmpty()) {
				List<Long> buildingIds = building.stream().map(BuildingEntity::getId).collect(Collectors.toList());
				List<EnergySourceEntity> energySources = erepo.findByBuildingIdsAndStartDate(buildingIds, startDate);
				return getAnalyticsList(energySources);
			}
			return null;
		} catch (Exception e) {
			logger.error("Exception Occured while fetching consumption details for the neighborhood:" + e.getMessage());
		}
		return null;
	}

	public List<AnalyticsResponse> getBuildingsByCity(String city, LocalDate startDate) {
		try {
			List<BuildingEntity> building = brepo.findByCity(city);
			if (building != null && !building.isEmpty()) {
				List<Long> buildingIds = building.stream().map(BuildingEntity::getId).collect(Collectors.toList());
				List<EnergySourceEntity> energySources = erepo.findByBuildingIdsAndStartDate(buildingIds, startDate);
				return getAnalyticsList(energySources);
			}
			return null;
		} catch (Exception e) {
			logger.error("Exception Occured while fetching consumption details for the city:" + e.getMessage());
		}
		return null;
	}

	private List<AnalyticsResponse> getAnalyticsList(List<EnergySourceEntity> energySources) {
		return energySources.stream().map(e -> {
			AnalyticsResponse res = new AnalyticsResponse();
			BuildingEntity b = e.getBuilding();
			res.setAddress(b.getAddress());
			res.setCity(b.getCity());
			res.setZipcode(b.getZipCode());
			res.setConsumption(e.getConsumption());
			res.setEnergyType(e.getEnergyType().getName());
			res.setUnit(e.getEnergyType().getUnit());
			res.setProvider(e.getEnergyType().getProvider());

			return res;
		}).sorted(Comparator.comparing(AnalyticsResponse::getConsumption).reversed()).collect(Collectors.toList());
	}
}
