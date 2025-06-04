package de.fh.energymanagementservice.domain.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnergySourceResponse {

	private String ownerEmail;
	private String energyType;
	private LocalDate startDate;
	private LocalDate endDate;
	private int consumption;
	private String status;
}
