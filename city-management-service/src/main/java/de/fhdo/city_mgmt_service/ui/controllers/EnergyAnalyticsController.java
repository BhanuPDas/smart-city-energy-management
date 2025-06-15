package de.fhdo.city_mgmt_service.ui.controllers;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.fhdo.city_mgmt_service.aspect.auth.UserToken;
import de.fhdo.city_mgmt_service.domain.request.EnergyAnalyticsRequest;
import de.fhdo.city_mgmt_service.domain.response.AnalyticsResponse;
import de.fhdo.city_mgmt_service.dto.EnergyAnalyticsDTO;
import de.fhdo.city_mgmt_service.exception.UserException;
import de.fhdo.city_mgmt_service.service.EnergyAnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@RequestMapping("/api/v1")
@Tag(name = "City Management for energy mgmt", description = "APIs for managing building and energy sources")
public class EnergyAnalyticsController {

	private final Logger logger = LoggerFactory.getLogger(EnergyAnalyticsController.class);
	@Autowired
	private EnergyAnalyticsService service;
	@Autowired
	private UserToken token;

	@PostMapping("/getUsage")
	@Operation(summary = "GetConsumptionReport")
	@ApiResponse(responseCode = "200", description = "Received Report")
	public String viewConsumptionReport(@ModelAttribute("consumption") EnergyAnalyticsDTO usage, Model model)
			throws UserException {
		try {
			EnergyAnalyticsRequest request = new EnergyAnalyticsRequest();
			request.setCity(usage.getCity());
			request.setOwnerEmail(usage.getOwnerEmail());
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			request.setStartDate(usage.getStartDate().format(format));
			request.setZipCode(usage.getZipCode());
			List<AnalyticsResponse> response = service.getAnaltics(request);
			if (response != null && !response.isEmpty()) {
				logger.info("Energy Consumption Report fetched successfully");
				if (token.getRole().equalsIgnoreCase("city_planner")) {
					EnergyAnalyticsDTO report = new EnergyAnalyticsDTO();
					model.addAttribute("consumption", report);
					model.addAttribute("usage", response);
					String msg = "Consumption Report fetched successfully";
					model.addAttribute("msg", msg);
					return "planner_consumption_report";
				} else {
					EnergyAnalyticsDTO report = new EnergyAnalyticsDTO();
					model.addAttribute("consumption", report);
					model.addAttribute("usage", response);
					double avg = response.stream().mapToInt(AnalyticsResponse::getConsumption).average().getAsDouble();
					String msg = "";
					if (usage.getOwnerEmail() != null && !usage.getOwnerEmail().isBlank()) {
						msg = "Individual Average Consumption is: " + avg;
					} else {
						msg = "Neighborhood Average Consumption is: " + avg;
					}
					model.addAttribute("msg", msg);
					return "citizen_consumption_report";
				}
			} else {
				EnergyAnalyticsDTO report = new EnergyAnalyticsDTO();
				String msg = "Consumption Report is not available.";
				model.addAttribute("consumption", report);
				model.addAttribute("msg", msg);
				logger.error("Consumption report not available");
				if (token.getRole().equalsIgnoreCase("city_planner"))
					return "planner_consumption_report";
				else
					return "citizen_consumption_report";

			}
		} catch (Exception e) {
			throw new UserException("Application has encountered some issue, please register later.");
		}
	}
}
