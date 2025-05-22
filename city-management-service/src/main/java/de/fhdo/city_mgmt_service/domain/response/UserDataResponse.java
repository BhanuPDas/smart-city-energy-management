package de.fhdo.city_mgmt_service.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDataResponse {

	private int userId;
	private String name;
	private String email;
	private String phone;
	private String role;

}
