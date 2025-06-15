package de.fhdo.city_mgmt_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserLoginDTO {

	private String email;
	private String password;
}
