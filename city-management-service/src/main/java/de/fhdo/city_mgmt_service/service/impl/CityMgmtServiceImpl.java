package de.fhdo.city_mgmt_service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.fhdo.city_mgmt_service.domain.request.UserRegistrationRequest;
import de.fhdo.city_mgmt_service.domain.response.UserRegistrationResponse;
import de.fhdo.city_mgmt_service.exception.UserException;
import de.fhdo.city_mgmt_service.service.CityMgmtService;

@Service
public class CityMgmtServiceImpl implements CityMgmtService {

	@Autowired
	public RestTemplate rest;

	@Autowired
	private ObjectMapper obj;

	@Value("${user_mgmt.url}")
	private String user_mgmt_url;

	public String registerUser(UserRegistrationRequest request) throws UserException {
		if (request != null) {
			String url = UriComponentsBuilder.fromUriString(user_mgmt_url + "/v1/users/register").toUriString();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<UserRegistrationRequest> entity = new HttpEntity<>(request, headers);
			try {
				ResponseEntity<UserRegistrationResponse> resp = rest.exchange(url, HttpMethod.POST, entity,
						UserRegistrationResponse.class);
				if (resp.getBody() != null) {
					return "success";
				}
			} catch (HttpClientErrorException.BadRequest ex) {
				return "failure";
			} catch (Exception e) {
				throw new UserException("Exception occured during user registration. " + e.getMessage());
			}
		}
		return "failure";
	}
}
