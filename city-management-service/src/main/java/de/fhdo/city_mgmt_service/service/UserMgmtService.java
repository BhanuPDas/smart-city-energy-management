package de.fhdo.city_mgmt_service.service;

import de.fhdo.city_mgmt_service.domain.request.UserLoginRequest;
import de.fhdo.city_mgmt_service.domain.request.UserRegistrationRequest;
import de.fhdo.city_mgmt_service.exception.UserException;

public interface UserMgmtService {

	public String registerUser(UserRegistrationRequest request) throws UserException;

	public String loginUser(UserLoginRequest request) throws UserException;

	public void verifyToken() throws UserException;

}
