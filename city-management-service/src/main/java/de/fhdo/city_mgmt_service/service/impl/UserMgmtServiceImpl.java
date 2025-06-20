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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.fhdo.city_mgmt_service.aspect.auth.UserToken;
import de.fhdo.city_mgmt_service.constant.CityManagementConstants;
import de.fhdo.city_mgmt_service.domain.entity.CityMgmtEntity;
import de.fhdo.city_mgmt_service.domain.request.UserLoginRequest;
import de.fhdo.city_mgmt_service.domain.request.UserRegistrationRequest;
import de.fhdo.city_mgmt_service.domain.response.TokenVerifyResponse;
import de.fhdo.city_mgmt_service.domain.response.UserLoginReponse;
import de.fhdo.city_mgmt_service.domain.response.UserRegistrationResponse;
import de.fhdo.city_mgmt_service.exception.UserException;
import de.fhdo.city_mgmt_service.repository.CityMgmtRepository;
import de.fhdo.city_mgmt_service.service.UserMgmtService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class UserMgmtServiceImpl implements UserMgmtService {

	private final Logger logger = LoggerFactory.getLogger(UserMgmtServiceImpl.class);

	@Autowired
	public RestTemplate rest;

	@Autowired
	private ObjectMapper obj;

	@Autowired
	private CityMgmtRepository repo;

	@Autowired
	private UserToken token;

	@Value("${user_mgmt.url}")
	private String user_mgmt_url;

	@CircuitBreaker(name = "register")
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
					logger.info("User:" + resp.getBody().getUserId() + "registered");
					return "success";
				}
			} catch (HttpClientErrorException.BadRequest ex) {
				logger.error("Exception for user:" + request.getEmail() + ex.getMessage());
				return "failure";
			} catch (Exception e) {
				logger.error("Exception for user:" + request.getEmail() + e.getMessage());
				throw new UserException("Exception occured during user registration. " + e.getMessage());
			}
		}
		return "failure";
	}

	@CircuitBreaker(name = "login")
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
					token.setUserId(resp.getBody().getUser().getUserId());
					token.setEmail(resp.getBody().getUser().getEmail());
					token.setName(resp.getBody().getUser().getName());
					token.setPhone(resp.getBody().getUser().getPhone());
					token.setRole(resp.getBody().getUser().getRole());
					logger.info("Login successful for user" + resp.getBody().getUser().getUserId());
					if (resp.getBody().getUser().getRole().equalsIgnoreCase("city_planner"))
						return "city_planner";
					else
						return "citizen";
				}
			} catch (HttpClientErrorException.Unauthorized ex) {
				logger.error("Login failed for user: " + request.getEmail() + ex.getMessage());
				return "failure";
			} catch (Exception e) {
				logger.error("Login failed for user: " + request.getEmail() + e.getMessage());
				throw new UserException("Exception occured during user login. " + e.getMessage());
			}
		}
		return "failure";
	}

	@CircuitBreaker(name = "tokenVerify")
	public void verifyToken() throws UserException {

		String url = UriComponentsBuilder.fromUriString(user_mgmt_url + "/v1/auth/verify").toUriString();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token.getToken());
		HttpEntity entity = new HttpEntity(headers);
		try {
			ResponseEntity<TokenVerifyResponse> resp = rest.exchange(url, HttpMethod.POST, entity,
					TokenVerifyResponse.class);
			if (resp.getBody() != null) {
				String status = resp.getBody().getStatus();
				logger.info("token status is:" + status);
			}
		} catch (HttpClientErrorException.Unauthorized e) {
			logger.error("Exception while verifying token:" + e.getMessage());
			throw new UserException("Session Terminated. Please login Again. ");
		} catch (Exception e) {
			logger.error("Exception while verifying token:" + e.getMessage());
			throw new UserException("Application is facing some technical issue, try again later.");
		}
	}
}
