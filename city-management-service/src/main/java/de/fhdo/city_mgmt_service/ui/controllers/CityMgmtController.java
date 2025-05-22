package de.fhdo.city_mgmt_service.ui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.fhdo.city_mgmt_service.domain.request.UserRegistrationRequest;
import de.fhdo.city_mgmt_service.dto.UserRegistrationDTO;
import de.fhdo.city_mgmt_service.service.CityMgmtService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/api/v1")
public class CityMgmtController {

	@Autowired
	private CityMgmtService service;

	@PostMapping("/register")
	public String registerUser(@Valid @ModelAttribute("user") UserRegistrationDTO user, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			return "register";
		}
		UserRegistrationRequest request = new UserRegistrationRequest(user.getName(), user.getEmail(), user.getPhone(),
				user.getRole(), user.getPassword());
		try {
			String status = service.registerUser(request);
			if(status.equalsIgnoreCase("success")) {
			model.addAttribute("user", new UserRegistrationDTO());
			model.addAttribute("msg", "Successfully Registered. Please Login.");
			return "register";
			} else {
				model.addAttribute("user", new UserRegistrationDTO());
				model.addAttribute("msg", "Registration Failed, Please Try Again.");
				return "register";
			}
		} catch (Exception e) {
			model.addAttribute("msg", "Application has encountered some issue, please login later.");
			return "app_error";
		}
	}
}
