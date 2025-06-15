package de.fhdo.city_mgmt_service.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginReponse {

	private String access_token;
	private UserDataResponse user;

}
