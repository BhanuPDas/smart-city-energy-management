package de.fhdo.city_mgmt_service.ui.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import de.fhdo.city_mgmt_service.dto.BuildingEnergyDTO;
import de.fhdo.city_mgmt_service.dto.EnergySourceDTO;
import de.fhdo.city_mgmt_service.dto.UserLoginDTO;
import de.fhdo.city_mgmt_service.dto.UserRegistrationDTO;

@Controller
public class SmartCityUIController {

	@GetMapping("/home-cp")
	public String viewHomeCP(Model model) {
		return "welcome_city_planner";
	}
	
	@GetMapping("/home-ct")
	public String viewHomeCT(Model model) {
		return "welcome_city_planner";
	}

	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("user", new UserRegistrationDTO());
		return "register";

	}

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("user", new UserLoginDTO());
		return "login";

	}

	@GetMapping("/addEditBuilding")
	public String addEditBuilding(Model model) {
		model.addAttribute("building", new BuildingEnergyDTO());
		return "planner_energy_source";

	}

	@GetMapping("/viewBuildingEnergyUsage")
	public String viewBuildingEnergyUsage(Model model) {
		return "planner_consumption_report";

	}
	
	@GetMapping("/addEditEnergy")
	public String addEditEnergy(Model model) {
		model.addAttribute("energy", new EnergySourceDTO());
		return "citizen_energy_source";

	}

	@GetMapping("/viewEnergyUsage")
	public String viewResidentEnergyUsage(Model model) {
		return "citizen_consumption_report";

	}
	
	@GetMapping("/viewRecommendations")
	public String viewRecommendations(Model model) {
		return "citizen_opt_recommendations";

	}

}
