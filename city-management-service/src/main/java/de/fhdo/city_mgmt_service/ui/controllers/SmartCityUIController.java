package de.fhdo.city_mgmt_service.ui.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import de.fhdo.city_mgmt_service.dto.BuildingEnergyDTO;
import de.fhdo.city_mgmt_service.dto.EnergyAnalyticsDTO;
import de.fhdo.city_mgmt_service.dto.EnergySourceDTO;
import de.fhdo.city_mgmt_service.dto.UserLoginDTO;
import de.fhdo.city_mgmt_service.dto.UserRegistrationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@Tag(name = "Smart City Management UI", description = "APIs for smart city management UI")
public class SmartCityUIController {

	@GetMapping("/home-cp")
	public String viewHomeCP(Model model) {
		return "welcome_city_planner";
	}

	@GetMapping("/home-ct")
	public String viewHomeCT(Model model) {
		return "welcome_citizen";
	}

	@Operation(summary = "Registration")
	@ApiResponse(responseCode = "200", description = "Registration Successfull")
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("user", new UserRegistrationDTO());
		return "register";

	}

	@Operation(summary = "Login")
	@ApiResponse(responseCode = "200", description = "Login Successfull")
	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("user", new UserLoginDTO());
		return "login";

	}

	@GetMapping("/addEditBuilding")
	@Operation(summary = "AddBuildingEnergy")
	@ApiResponse(responseCode = "200", description = "Building Registration Successfull")
	public String addEditBuilding(Model model) {
		model.addAttribute("building", new BuildingEnergyDTO());
		return "planner_energy_source";

	}

	@GetMapping("/viewPlannerUsage")
	@Operation(summary = "GetConsumptionReport")
	@ApiResponse(responseCode = "200", description = "Received Report")
	public String viewBuildingEnergyUsage(Model model) {
		EnergyAnalyticsDTO consumption = new EnergyAnalyticsDTO();
		model.addAttribute("consumption", consumption);
		return "planner_consumption_report";

	}

	@GetMapping("/addEditEnergy")
	@Operation(summary = "AddEnergySource")
	@ApiResponse(responseCode = "200", description = "Adding New Energy Source")
	public String addEditEnergy(Model model) {
		model.addAttribute("energy", new EnergySourceDTO());
		return "citizen_energy_source";

	}

	@GetMapping("/viewCitizenUsage")
	@Operation(summary = "GetConsumptionReport")
	@ApiResponse(responseCode = "200", description = "Received Report")
	public String viewResidentEnergyUsage(Model model) {
		EnergyAnalyticsDTO consumption = new EnergyAnalyticsDTO();
		model.addAttribute("consumption", consumption);
		return "citizen_consumption_report";

	}

	@Operation(summary = "GetRecommendations")
	@ApiResponse(responseCode = "200", description = "Received Recommendations")
	@GetMapping("/viewRecommendations")
	public String viewRecommendations(Model model) {
		return "citizen_opt_recommendations";

	}

}
