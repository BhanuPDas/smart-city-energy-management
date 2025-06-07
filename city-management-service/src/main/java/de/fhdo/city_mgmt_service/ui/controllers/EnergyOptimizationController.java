package de.fhdo.city_mgmt_service.ui.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.fhdo.city_mgmt_service.aspect.auth.UserToken;
import de.fhdo.city_mgmt_service.exception.UserException;
import de.fhdo.city_mgmt_service.service.EnergyOptimizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Controller
@RequestMapping("/api/v1")
public class EnergyOptimizationController {

	private final Logger logger = LoggerFactory.getLogger(EnergyOptimizationController.class);
	@Autowired
	private EnergyOptimizationService service;
	@Autowired
	private UserToken token;

	@GetMapping("/optimize")
	@Operation(summary = "GetRecommendations")
	@ApiResponse(responseCode = "200", description = "Received Recommendations")
	public String fetchRecommendations(Model model) throws UserException {
		try {
			String msg = service.getRecommendations();
			if (msg != null) {
				model.addAttribute("msg", msg);
				logger.info(
						"Energy Optimizations recommendations fetched succesfully for the owner: " + token.getEmail());
				return "citizen_opt_recommendations";
			} else {
				model.addAttribute("msg", "No recommendations could be fetched. Please Try again later.");
				logger.error("No recommendations could be fetched for owner: " + token.getEmail());
				return "citizen_opt_recommendations";
			}
		} catch (Exception e) {
			throw new UserException("Application has encountered some issue, please register later.");
		}
	}
}
