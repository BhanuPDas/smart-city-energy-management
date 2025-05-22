package de.fhdo.city_mgmt_service.ui.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import de.fhdo.city_mgmt_service.dto.UserLoginDTO;
import de.fhdo.city_mgmt_service.dto.UserRegistrationDTO;

@Controller
public class SmartCityUIController {

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

}
