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
import de.fhdo.city_mgmt_service.domain.request.EnergyMgmtRequest;
import de.fhdo.city_mgmt_service.domain.response.EnergyMgmtResponse;
import de.fhdo.city_mgmt_service.exception.UserException;
import de.fhdo.city_mgmt_service.repository.CityMgmtRepository;
import de.fhdo.city_mgmt_service.service.EnergyMgmtService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class EnergyMgmtServiceImpl implements EnergyMgmtService {

	private final Logger logger = LoggerFactory.getLogger(EnergyMgmtServiceImpl.class);
	@Autowired
	public RestTemplate rest;

	@Autowired
	private ObjectMapper obj;
	@Autowired
	private CityMgmtRepository repo;
	@Autowired
	private UserToken token;
	@Value("${energy_mgmt.url}")
	private String energy_mgmt_url;

	@CircuitBreaker(name = "BuildingRegister")
	public EnergyMgmtResponse addBuildingEnergy(EnergyMgmtRequest request) throws UserException {
		EnergyMgmtResponse response = new EnergyMgmtResponse();
		if (request != null && token.getRole().equalsIgnoreCase("city_planner")) {
			String url = UriComponentsBuilder.fromUriString(energy_mgmt_url + "/api/v1/resident-energy/add")
					.toUriString();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<EnergyMgmtRequest> entity = new HttpEntity<>(request, headers);
			try {
				ResponseEntity<EnergyMgmtResponse> resp = rest.exchange(url, HttpMethod.POST, entity,
						EnergyMgmtResponse.class);
				if (resp.getBody() != null) {
					response.setAddress(resp.getBody().getAddress());
					response.setCity(resp.getBody().getCity());
					response.setConsumption(resp.getBody().getConsumption());
					response.setEndDate(resp.getBody().getEndDate());
					response.setEnergyType(resp.getBody().getEnergyType());
					response.setFloorArea(resp.getBody().getFloorArea());
					response.setOwnerEmail(resp.getBody().getOwnerEmail());
					response.setStartDate(resp.getBody().getStartDate());
					response.setStatus(resp.getBody().getStatus());
					response.setZipCode(resp.getBody().getZipCode());
					CityMgmtEntity repoEntity = new CityMgmtEntity();
					repoEntity.setApp(CityManagementConstants.ENERGY_MGMT);
					repoEntity.setRequest(obj.writeValueAsString(request));
					repoEntity.setResponse(obj.writeValueAsString(resp.getBody()));
					repo.save(repoEntity);
					logger.info("Resident Building and energy source is registered for owner: "
							+ resp.getBody().getOwnerEmail());
					return response;
				}

			} catch (Exception e) {
				logger.error("Exception occured during building and energy source registration: " + e.getMessage());
				throw new UserException(
						"Exception occured during building and energy source registration. " + e.getMessage());
			}
		}
		return null;
	}

	@CircuitBreaker(name = "BuildingUpdate")
	public EnergyMgmtResponse updateBuildingEnergy(EnergyMgmtRequest request) throws UserException {
		EnergyMgmtResponse response = new EnergyMgmtResponse();
		if (request != null && token.getRole().equalsIgnoreCase("city_planner")) {
			String url = UriComponentsBuilder.fromUriString(energy_mgmt_url + "/api/v1/resident-energy/update")
					.toUriString();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<EnergyMgmtRequest> entity = new HttpEntity<>(request, headers);
			try {
				ResponseEntity<EnergyMgmtResponse> resp = rest.exchange(url, HttpMethod.PUT, entity,
						EnergyMgmtResponse.class);
				if (resp.getBody() != null) {
					response.setAddress(resp.getBody().getAddress());
					response.setCity(resp.getBody().getCity());
					response.setConsumption(resp.getBody().getConsumption());
					response.setEndDate(resp.getBody().getEndDate());
					response.setEnergyType(resp.getBody().getEnergyType());
					response.setFloorArea(resp.getBody().getFloorArea());
					response.setOwnerEmail(resp.getBody().getOwnerEmail());
					response.setStartDate(resp.getBody().getStartDate());
					response.setStatus(resp.getBody().getStatus());
					response.setZipCode(resp.getBody().getZipCode());
					CityMgmtEntity repoEntity = new CityMgmtEntity();
					repoEntity.setApp(CityManagementConstants.ENERGY_MGMT);
					repoEntity.setRequest(obj.writeValueAsString(request));
					repoEntity.setResponse(obj.writeValueAsString(resp.getBody()));
					repo.save(repoEntity);
					logger.info("Resident Building and energy source is updated for owner: "
							+ resp.getBody().getOwnerEmail());
					return response;
				}

			} catch (Exception e) {
				logger.error("Exception occured while updating building and energy source: " + e.getMessage());
				throw new UserException(
						"Exception occured while updating building and energy source: " + e.getMessage());
			}
		}
		return null;
	}
}
