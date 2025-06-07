package de.fhdo.city_mgmt_service.service;

import de.fhdo.city_mgmt_service.domain.request.UserLoginRequest;
import de.fhdo.city_mgmt_service.domain.request.UserRegistrationRequest;

public interface UserMgmtService {

	public String registerUser(UserRegistrationRequest request) throws Exception;
	public String loginUser(UserLoginRequest request) throws Exception;

}
