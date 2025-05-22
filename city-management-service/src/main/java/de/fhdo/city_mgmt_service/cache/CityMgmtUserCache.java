package de.fhdo.city_mgmt_service.cache;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import de.fhdo.city_mgmt_service.dto.UserDataDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
public class CityMgmtUserCache {

	@Cacheable(value = "userdata", key = "#user.access_token")
	public UserDataDTO userData(UserDataDTO user) {
		return user;
	}
}
