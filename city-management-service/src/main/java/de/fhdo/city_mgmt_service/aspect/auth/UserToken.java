package de.fhdo.city_mgmt_service.aspect.auth;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
@Scope("singleton")
public class UserToken {

	private String token;
	private int userId;
	private String name;
	private String email;
	private String phone;
	private String role;
}
