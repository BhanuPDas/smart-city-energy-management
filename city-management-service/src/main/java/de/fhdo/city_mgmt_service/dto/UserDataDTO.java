package de.fhdo.city_mgmt_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDataDTO {
	
	private String access_token;
	private int userId;
	private String name;
	private String email;
	private String phone;
	private String role;

}
