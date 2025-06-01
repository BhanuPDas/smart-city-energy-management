package com.fhdo.energy_analytics_service.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(value = Include.NON_NULL)
public class AnalyticsResponse {

	private String address;
	private int consumption;
	private String energyType;
	private String unit;
	private String city;
	private int zipcode;
	private String provider;
}
