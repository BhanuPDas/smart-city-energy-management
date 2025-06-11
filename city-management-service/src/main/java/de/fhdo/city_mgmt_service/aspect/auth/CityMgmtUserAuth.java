package de.fhdo.city_mgmt_service.aspect.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import de.fhdo.city_mgmt_service.exception.UserException;
import de.fhdo.city_mgmt_service.service.UserMgmtService;

@Aspect
@Component
public class CityMgmtUserAuth {

	private final Logger logger = LoggerFactory.getLogger(CityMgmtUserAuth.class);

	@Autowired
	private UserMgmtService service;

	@Before("execution(* de.fhdo.city_mgmt_service.service.impl.EnergyMgmtServiceImpl.*(..)) || "
			+ "execution(* de.fhdo.city_mgmt_service.service.impl.EnergyOptimizationServiceImpl.*(..)) || "
			+ "execution(* de.fhdo.city_mgmt_service.service.impl.EnergyAnalyticsServiceImpl.*(..))")
	public void verifyToken() throws UserException {

		logger.info("Authenticating User Token");
		try {
			service.verifyToken();
		} catch (HttpClientErrorException.Unauthorized e) {
			logger.error("Exception while verifying token:" + e.getMessage());
			throw new UserException("Session Terminated. Please login Again. ");
		} catch (Exception e) {
			logger.error("Exception while verifying token:" + e.getMessage());
			throw new UserException("Application is facing some technical issue, try again later.");
		}
	}

}
