package de.fhdo.city_mgmt_service.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.fhdo.city_mgmt_service.aspect.auth.UserToken;
import de.fhdo.city_mgmt_service.constant.CityManagementConstants;
import de.fhdo.city_mgmt_service.domain.entity.CityMgmtEntity;
import de.fhdo.city_mgmt_service.domain.request.EnergyAnalyticsRequest;
import de.fhdo.city_mgmt_service.domain.response.AnalyticsResponse;
import de.fhdo.city_mgmt_service.exception.UserException;
import de.fhdo.city_mgmt_service.repository.CityMgmtRepository;
import de.fhdo.city_mgmt_service.service.EnergyAnalyticsService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class EnergyAnalyticsServiceImpl implements EnergyAnalyticsService {

	private final Logger logger = LoggerFactory.getLogger(EnergyAnalyticsServiceImpl.class);
	@Autowired
	public RestTemplate rest;
	@Autowired
	private ObjectMapper obj;
	@Autowired
	private CityMgmtRepository repo;
	@Value(value = "${energy_analytics.url}")
	private String energy_analytics_url;
	@Autowired
	private UserToken token;

	@CircuitBreaker(name = "energyAnalytics")
	public List<AnalyticsResponse> getAnaltics(EnergyAnalyticsRequest request) throws UserException {

		if (request != null) {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity entity = new HttpEntity<>(headers);
			String requestParam = "";
			String url = "";
			if (request.getOwnerEmail() != null && !request.getOwnerEmail().isBlank()) {
				if (token.getRole().equalsIgnoreCase("city_planner") || (token.getRole().equalsIgnoreCase("citizen")
						&& token.getEmail().equalsIgnoreCase(request.getOwnerEmail())))
					url = UriComponentsBuilder.fromUriString(energy_analytics_url + "/api/v1/analytics/email")
							.queryParam("email", request.getOwnerEmail())
							.queryParam("startDate", request.getStartDate()).toUriString();
				requestParam = request.getOwnerEmail() + " " + request.getStartDate();
			} else if (request.getZipCode() != 0) {
				url = UriComponentsBuilder.fromUriString(energy_analytics_url + "/api/v1/analytics/zipcode")
						.queryParam("zipCode", request.getZipCode()).queryParam("startDate", request.getStartDate())
						.toUriString();
				requestParam = requestParam + request.getZipCode() + " " + request.getStartDate();
			} else if (request.getCity() != null && !request.getCity().isBlank()) {
				url = UriComponentsBuilder.fromUriString(energy_analytics_url + "/api/v1/analytics/city")
						.queryParam("city", request.getCity()).queryParam("startDate", request.getStartDate())
						.toUriString();
				requestParam = request.getCity() + " " + request.getStartDate();
			} else {
				return null;
			}
			try {
				ResponseEntity<List<AnalyticsResponse>> resp = rest.exchange(url, HttpMethod.GET, entity,
						new ParameterizedTypeReference<List<AnalyticsResponse>>() {
						});
				if (resp.getBody() != null && !resp.getBody().isEmpty()) {
					List<AnalyticsResponse> response = resp.getBody();
					CityMgmtEntity repoEntity = new CityMgmtEntity();
					repoEntity.setApp(CityManagementConstants.ENERGY_ANALYTICS);
					repoEntity.setRequest(obj.writeValueAsString(requestParam));
					repoEntity.setResponse(obj.writeValueAsString(resp.getBody()));
					repo.save(repoEntity);
					logger.info("Energy Consumption Report Received: ");
					return response;
				}
			} catch (Exception e) {
				logger.error("Exception occured while fetching energy usage report: " + e.getMessage());
				throw new UserException("Exception occured while fetching energy usage report: " + e.getMessage());
			}
		}
		return null;
	}

}
