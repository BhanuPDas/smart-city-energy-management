package de.fhdo.city_mgmt_service.aspect.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import de.fhdo.city_mgmt_service.exception.UserException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Aspect
@Component
public class CityMgmtUserAuth {

	private final Logger logger = LoggerFactory.getLogger(CityMgmtUserAuth.class);

	@Value("${user_mgmt.url}")
	private String user_mgmt_url;

	@Autowired
	private RestTemplate rest;

	@Autowired
	private UserToken token;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@AfterReturning("execution(* de.fhdo.city_mgmt_service.service.impl.CityMgmtServiceImpl.loginUser(..))")
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
