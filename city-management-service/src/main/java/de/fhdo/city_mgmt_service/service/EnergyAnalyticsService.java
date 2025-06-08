package de.fhdo.city_mgmt_service.service;

import java.util.List;

import de.fhdo.city_mgmt_service.domain.request.EnergyAnalyticsRequest;
import de.fhdo.city_mgmt_service.domain.response.AnalyticsResponse;
import de.fhdo.city_mgmt_service.exception.UserException;

public interface EnergyAnalyticsService {

	public List<AnalyticsResponse> getAnaltics(EnergyAnalyticsRequest request) throws UserException ;
}
