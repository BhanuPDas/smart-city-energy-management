package de.fh.energymanagementservice.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnergySourceRequest {

	private String ownerEmail;
	private String energyType;
	private String startDate;
	private String endDate;
	private int consumption;
}
