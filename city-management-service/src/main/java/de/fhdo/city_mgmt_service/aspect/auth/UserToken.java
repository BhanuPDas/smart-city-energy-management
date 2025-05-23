package de.fhdo.city_mgmt_service.aspect.auth;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class UserToken {

	private String token;
}
