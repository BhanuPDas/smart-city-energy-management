package de.fhdo.city_mgmt_service.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import de.fhdo.city_mgmt_service.exception.UserException;
import de.fhdo.city_mgmt_service.repository.CityMgmtRepository;
import de.fhdo.city_mgmt_service.service.EnergyOptimizationService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class EnergyOptimizationServiceImpl implements EnergyOptimizationService {

	private final Logger logger = LoggerFactory.getLogger(EnergyOptimizationServiceImpl.class);
	@Autowired
	public RestTemplate rest;
	@Autowired
	private ObjectMapper obj;
	@Autowired
	private CityMgmtRepository repo;
	@Autowired
	private UserToken token;
	@Value(value = "${energy_opt.url}")
	private String energy_opt_url;

	@CircuitBreaker(name = "energyRecommend")
	public String getRecommendations() throws UserException {
		if (token.getRole().equalsIgnoreCase("citizen")) {
			String url = UriComponentsBuilder.fromUriString(energy_opt_url + "/api/v1/optimize")
					.queryParam("email", token.getEmail()).toUriString();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity entity = new HttpEntity<>(headers);
			try {
				ResponseEntity<String> resp = rest.exchange(url, HttpMethod.GET, entity, String.class);
				if (resp.getBody() != null) {
					CityMgmtEntity repoEntity = new CityMgmtEntity();
					repoEntity.setApp(CityManagementConstants.ENERGY_OPTIMIZATION);
					repoEntity.setRequest(obj.writeValueAsString(token.getEmail()));
					repoEntity.setResponse(obj.writeValueAsString(resp.getBody()));
					repo.save(repoEntity);
					logger.info("Energy Optimization Recommendations for owner: " + resp.getBody());
					return resp.getBody();
				}
			} catch (Exception e) {
				logger.error("Exception occured while fetching recommendations: " + e.getMessage());
				throw new UserException("Exception occured while fetching recommendations: " + e.getMessage());
			}

		}
		return null;
	}

}
