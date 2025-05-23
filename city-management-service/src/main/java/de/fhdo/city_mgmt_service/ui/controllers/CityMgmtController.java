package de.fhdo.city_mgmt_service.ui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.fhdo.city_mgmt_service.domain.request.UserLoginRequest;
import de.fhdo.city_mgmt_service.domain.request.UserRegistrationRequest;
import de.fhdo.city_mgmt_service.dto.UserLoginDTO;
import de.fhdo.city_mgmt_service.dto.UserRegistrationDTO;
import de.fhdo.city_mgmt_service.exception.UserException;
import de.fhdo.city_mgmt_service.service.CityMgmtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/api/v1")
@Tag(name = "City Management Controller", description = "APIs for City Management")
public class CityMgmtController {

	@Autowired
	private CityMgmtService service;

	@PostMapping("/register")
	@Operation(summary = "Registration")
	@ApiResponse(responseCode = "200", description = "Registration Successfull")
	public String registerUser(@Valid @ModelAttribute("user") UserRegistrationDTO user, BindingResult result,
			Model model) throws UserException {
		if (result.hasErrors()) {
			return "register";
		}
		UserRegistrationRequest request = new UserRegistrationRequest(user.getName(), user.getEmail(), user.getPhone(),
				user.getRole(), user.getPassword());
		try {
			String status = service.registerUser(request);
			if (status.equalsIgnoreCase("success")) {
				model.addAttribute("user", new UserRegistrationDTO());
				model.addAttribute("msg", "Successfully Registered. Please Login.");
				return "register";
			} else {
				model.addAttribute("user", new UserRegistrationDTO());
				model.addAttribute("msg", "Registration Failed, Please Try Again.");
				return "register";
			}
		} catch (Exception e) {
			throw new UserException("Application has encountered some issue, please register later.");
		}
	}

	@PostMapping("/login")
	@Operation(summary = "Login")
	@ApiResponse(responseCode = "200", description = "Login Successfull")
	public String loginUser(@Valid @ModelAttribute("user") UserLoginDTO user, Model model) throws UserException {
		UserLoginRequest request = new UserLoginRequest(user.getEmail(), user.getPassword());
		try {
			String status = service.loginUser(request);
			if (status.equalsIgnoreCase("city_planner")) {
				return "welcome_city_planner";
			} else if (status.equalsIgnoreCase("citizen")) {
				return "welcome_citizen";
			} else {
				model.addAttribute("user", new UserLoginDTO());
				model.addAttribute("msg", "User Not Found.");
				return "login";
			}
		} catch (Exception e) {
			throw new UserException("Application has encountered some issue, please login later.");
		}
	}
}
