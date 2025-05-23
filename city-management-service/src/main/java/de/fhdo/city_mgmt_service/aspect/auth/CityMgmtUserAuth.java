package de.fhdo.city_mgmt_service.aspect.auth;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import de.fhdo.city_mgmt_service.domain.response.TokenVerifyResponse;
import de.fhdo.city_mgmt_service.dto.UserDataDTO;
import de.fhdo.city_mgmt_service.exception.UserException;

@Aspect
@Component
public class CityMgmtUserAuth {

	@Value("${user_mgmt.url}")
	private String user_mgmt_url;

	@Autowired
	private RestTemplate rest;

	@Autowired
	private UserToken token;

	@AfterReturning("execution(* de.fhdo.city_mgmt_service.service.impl.CityMgmtServiceImpl.loginUser(..))")
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
				System.out.println("After verification -- " + status);
			}
		} catch (HttpClientErrorException.Unauthorized e) {
			removeUserData(token.getToken());
			throw new UserException("Session Terminated. Please login Again. ");
		} catch (Exception e) {
			removeUserData(token.getToken());
			throw new UserException("Application is facing some technical issue, try again later.");
		}
	}

	@Cacheable(value = "userdata", key = "#p0")
	public UserDataDTO fetchUserData(String token, UserDataDTO user) {
		return user;
	}

	@CacheEvict(value = "userdata", key = "#p0")
	private void removeUserData(String token) {

	}

}
