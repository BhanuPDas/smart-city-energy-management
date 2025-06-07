package de.fhdo.city_mgmt_service.service;

import de.fhdo.city_mgmt_service.exception.UserException;

public interface EnergyOptimizationService {

	public String getRecommendations() throws UserException;
}
