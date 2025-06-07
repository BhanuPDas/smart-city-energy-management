package de.fhdo.city_mgmt_service.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnergyMgmtResponse {

	private String city;
	private String address;
	private int zipCode;
	private String ownerEmail;
	private int floorArea;
	private String energyType;
	private String startDate;
	private String endDate;
	private int consumption;
	private String status;
}
