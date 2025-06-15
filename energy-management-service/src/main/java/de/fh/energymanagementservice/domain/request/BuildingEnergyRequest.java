package de.fh.energymanagementservice.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildingEnergyRequest {

	private String city;
	private String address;
	private int zipCode;
	private String ownerEmail;
	private int floorArea;
	private String energyType;
	private String startDate;
	private String endDate;
	private int consumption;
}
