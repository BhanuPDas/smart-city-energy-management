package de.fhdo.city_mgmt_service.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationRequest {

	private String name;
	private String email;
	private String phone;
	private String role;
	private String password;

}
