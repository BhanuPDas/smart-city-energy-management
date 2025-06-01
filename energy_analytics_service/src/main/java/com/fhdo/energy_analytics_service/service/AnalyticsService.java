package com.fhdo.energy_analytics_service.service;

import java.time.LocalDate;
import java.util.List;
import com.fhdo.energy_analytics_service.domain.response.AnalyticsResponse;

public interface AnalyticsService {

	public List<AnalyticsResponse> getBuildingByEmail(String email, LocalDate startDate);

	public List<AnalyticsResponse> getBuildingsByZipCode(Integer zipCode, LocalDate startDate);

	public List<AnalyticsResponse> getBuildingsByCity(String city, LocalDate startDate);

}
