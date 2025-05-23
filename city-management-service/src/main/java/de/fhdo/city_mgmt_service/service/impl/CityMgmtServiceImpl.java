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

import de.fhdo.city_mgmt_service.aspect.auth.CityMgmtUserAuth;
import de.fhdo.city_mgmt_service.aspect.auth.UserToken;
import de.fhdo.city_mgmt_service.constant.CityManagementConstants;
import de.fhdo.city_mgmt_service.domain.entity.CityMgmtEntity;
import de.fhdo.city_mgmt_service.domain.request.UserLoginRequest;
import de.fhdo.city_mgmt_service.domain.request.UserRegistrationRequest;
import de.fhdo.city_mgmt_service.domain.response.UserLoginReponse;
import de.fhdo.city_mgmt_service.domain.response.UserRegistrationResponse;
import de.fhdo.city_mgmt_service.dto.UserDataDTO;
import de.fhdo.city_mgmt_service.exception.UserException;
import de.fhdo.city_mgmt_service.repository.CityMgmtRepository;
import de.fhdo.city_mgmt_service.service.CityMgmtService;

@Service
public class CityMgmtServiceImpl implements CityMgmtService {

	@Autowired
	public RestTemplate rest;

	@Autowired
	private ObjectMapper obj;

	@Autowired
	private CityMgmtRepository repo;

	@Autowired
	private CityMgmtUserAuth cache;

	@Autowired
	private UserToken token;

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
					CityMgmtEntity repoEntity = new CityMgmtEntity();
					repoEntity.setApp(CityManagementConstants.USERS_MGMT);
					repoEntity.setRequest(obj.writeValueAsString(request));
					repoEntity.setResponse(obj.writeValueAsString(resp.getBody()));
					repo.save(repoEntity);
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

	public String loginUser(UserLoginRequest request) throws UserException {
		if (request != null) {
			String url = UriComponentsBuilder.fromUriString(user_mgmt_url + "/v1/auth/login").toUriString();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<UserLoginRequest> entity = new HttpEntity<>(request, headers);
			try {
				ResponseEntity<UserLoginReponse> resp = rest.exchange(url, HttpMethod.POST, entity,
						UserLoginReponse.class);
				if (resp.getBody() != null) {
					CityMgmtEntity repoEntity = new CityMgmtEntity();
					repoEntity.setApp(CityManagementConstants.USERS_MGMT);
					repoEntity.setRequest(obj.writeValueAsString(request));
					repoEntity.setResponse(obj.writeValueAsString(resp.getBody()));
					repo.save(repoEntity);
					token.setToken(resp.getBody().getAccess_token());
					UserDataDTO userdto = new UserDataDTO();
					userdto.setUserId(resp.getBody().getUser().getUserId());
					userdto.setEmail(resp.getBody().getUser().getEmail());
					userdto.setName(resp.getBody().getUser().getName());
					userdto.setPhone(resp.getBody().getUser().getPhone());
					userdto.setRole(resp.getBody().getUser().getRole());
					cache.fetchUserData(resp.getBody().getAccess_token(), userdto);
					if (resp.getBody().getUser().getRole().equalsIgnoreCase("city_planner"))
						return "city_planner";
					else
						return "citizen";
				}
			} catch (HttpClientErrorException.Unauthorized ex) {
				return "failure";
			} catch (Exception e) {
				throw new UserException("Exception occured during user login. " + e.getMessage());
			}
		}
		return "failure";
	}
}
