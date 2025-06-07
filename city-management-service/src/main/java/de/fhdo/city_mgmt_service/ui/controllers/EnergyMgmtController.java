package de.fhdo.city_mgmt_service.ui.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.fhdo.city_mgmt_service.domain.request.EnergyMgmtRequest;
import de.fhdo.city_mgmt_service.domain.request.EnergySourceRequest;
import de.fhdo.city_mgmt_service.domain.response.EnergyMgmtResponse;
import de.fhdo.city_mgmt_service.domain.response.EnergySourceResponse;
import de.fhdo.city_mgmt_service.dto.BuildingEnergyDTO;
import de.fhdo.city_mgmt_service.dto.EnergySourceDTO;
import de.fhdo.city_mgmt_service.exception.UserException;
import de.fhdo.city_mgmt_service.service.EnergyMgmtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@RequestMapping("/api/v1")
@Tag(name = "City Management Controller for energy mgmt", description = "APIs for managing building and energy sources")
public class EnergyMgmtController {

	private final Logger logger = LoggerFactory.getLogger(EnergyMgmtController.class);
	@Autowired
	private EnergyMgmtService energyService;

	@PostMapping("/addBuildingEnergy")
	@Operation(summary = "AddBuildingEnergy")
	@ApiResponse(responseCode = "200", description = "Building Registration Successfull")
	public String registerBuilding(@ModelAttribute("building") BuildingEnergyDTO building, Model model)
			throws UserException {

		EnergyMgmtRequest request = new EnergyMgmtRequest();
		request.setAddress(building.getAddress());
		request.setCity(building.getCity());
		request.setConsumption(building.getConsumption());
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		if (building.getEndDate() != null)
			request.setEndDate(building.getEndDate().format(format));
		else
			request.setEndDate("");
		request.setEnergyType(building.getEnergyType());
		request.setFloorArea(building.getFloorArea());
		request.setOwnerEmail(building.getOwnerEmail());
		if (building.getStartDate() != null)
			request.setStartDate(building.getStartDate().format(format));
		else
			request.setStartDate("");
		request.setZipCode(building.getZipCode());
		try {
			EnergyMgmtResponse response = energyService.addBuildingEnergy(request);
			if (response != null) {
				BuildingEnergyDTO bdto = new BuildingEnergyDTO();
				bdto.setAddress(response.getAddress());
				bdto.setCity(response.getCity());
				bdto.setConsumption(response.getConsumption());
				bdto.setEndDate(LocalDate.parse(response.getEndDate()));
				bdto.setEnergyType(response.getEnergyType());
				bdto.setFloorArea(response.getFloorArea());
				bdto.setOwnerEmail(response.getOwnerEmail());
				bdto.setStartDate(LocalDate.parse(response.getStartDate()));
				bdto.setZipCode(response.getZipCode());
				model.addAttribute("building", bdto);
				model.addAttribute("msg", response.getStatus());
				logger.info("Resident Building and energy source registerd for owner: " + building.getOwnerEmail());
				return "planner_energy_source";
			} else {
				BuildingEnergyDTO bdto = new BuildingEnergyDTO();
				String msg = "Resident Building and Energy Source Registration failed. Please try again later.";
				model.addAttribute("building", bdto);
				model.addAttribute("msg", msg);
				logger.error("Resident Building and energy source couldnot be registerd for owner: "
						+ building.getOwnerEmail());
				return "planner_energy_source";
			}

		} catch (Exception e) {

			throw new UserException("Application has encountered some issue, please register later.");
		}
	}

	@PostMapping("/updateBuildingEnergy")
	@Operation(summary = "EditBuildingEnergy")
	@ApiResponse(responseCode = "200", description = "Building Details Updated Successfull")
	public String updateBuilding(@ModelAttribute("building") BuildingEnergyDTO building, Model model)
			throws UserException {

		EnergyMgmtRequest request = new EnergyMgmtRequest();
		request.setAddress(building.getAddress());
		request.setCity(building.getCity());
		request.setConsumption(building.getConsumption());
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		if (building.getEndDate() != null)
			request.setEndDate(building.getEndDate().format(format));
		else
			request.setEndDate("");
		request.setEnergyType(building.getEnergyType());
		request.setFloorArea(building.getFloorArea());
		request.setOwnerEmail(building.getOwnerEmail());
		request.setStartDate(building.getStartDate().format(format));
		request.setZipCode(building.getZipCode());
		try {
			EnergyMgmtResponse response = energyService.updateBuildingEnergy(request);
			if (response != null) {
				BuildingEnergyDTO bdto = new BuildingEnergyDTO();
				bdto.setAddress(response.getAddress());
				bdto.setCity(response.getCity());
				bdto.setConsumption(response.getConsumption());
				bdto.setEndDate(LocalDate.parse(response.getEndDate()));
				bdto.setEnergyType(response.getEnergyType());
				bdto.setFloorArea(response.getFloorArea());
				bdto.setOwnerEmail(response.getOwnerEmail());
				bdto.setStartDate(LocalDate.parse(response.getStartDate()));
				bdto.setZipCode(response.getZipCode());
				model.addAttribute("building", bdto);
				model.addAttribute("msg", response.getStatus());
				logger.info("Resident Building and energy source updated for owner: " + building.getOwnerEmail());
				return "planner_energy_source";
			} else {
				BuildingEnergyDTO bdto = new BuildingEnergyDTO();
				String msg = "Resident Building and Energy Source update failed. Please try again later.";
				model.addAttribute("building", bdto);
				model.addAttribute("msg", msg);
				logger.error("Resident Building and energy source couldnot be updated for owner: "
						+ building.getOwnerEmail());
				return "planner_energy_source";
			}

		} catch (Exception e) {

			throw new UserException("Application has encountered some issue, please register later.");
		}
	}

	@PostMapping("/addEnergySource")
	@Operation(summary = "AddEnergySource")
	@ApiResponse(responseCode = "200", description = "Adding New Energy Source")
	public String addNewEnergySource(@ModelAttribute("energy") EnergySourceDTO energy, Model model)
			throws UserException {

		EnergySourceRequest request = new EnergySourceRequest();
		request.setConsumption(energy.getConsumption());
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		if (energy.getEndDate() != null)
			request.setEndDate(energy.getEndDate().format(format));
		else
			request.setEndDate("");
		request.setEnergyType(energy.getEnergyType());
		request.setOwnerEmail(energy.getOwnerEmail());
		request.setStartDate(energy.getStartDate().format(format));
		try {
			EnergySourceResponse response = energyService.addResidentEnergy(request);
			if (response != null) {
				EnergySourceDTO bdto = new EnergySourceDTO();
				bdto.setConsumption(response.getConsumption());
				bdto.setEndDate(LocalDate.parse(response.getEndDate()));
				bdto.setEnergyType(response.getEnergyType());
				bdto.setOwnerEmail(response.getOwnerEmail());
				bdto.setStartDate(LocalDate.parse(response.getStartDate()));
				model.addAttribute("energy", bdto);
				model.addAttribute("msg", response.getStatus());
				logger.info("New Energy source added for owner: " + energy.getOwnerEmail());
				return "citizen_energy_source";
			} else {
				EnergySourceDTO bdto = new EnergySourceDTO();
				String msg = "Error while adding new Energy Source. Please try again later.";
				model.addAttribute("energy", bdto);
				model.addAttribute("msg", msg);
				logger.error("Energy source couldnot be added for owner: " + energy.getOwnerEmail());
				return "citizen_energy_source";
			}

		} catch (Exception e) {

			throw new UserException("Application has encountered some issue, please register later.");
		}
	}

	@PostMapping("/updateEnergySource")
	@Operation(summary = "AddEnergySource")
	@ApiResponse(responseCode = "200", description = "Adding New Energy Source")
	public String updateEnergySource(@ModelAttribute("energy") EnergySourceDTO energy, Model model)
			throws UserException {

		EnergySourceRequest request = new EnergySourceRequest();
		request.setConsumption(energy.getConsumption());
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		if (energy.getEndDate() != null)
			request.setEndDate(energy.getEndDate().format(format));
		else
			request.setEndDate("");
		request.setEnergyType(energy.getEnergyType());
		request.setOwnerEmail(energy.getOwnerEmail());
		request.setStartDate(energy.getStartDate().format(format));
		try {
			EnergySourceResponse response = energyService.updateResidentEnergy(request);
			if (response != null) {
				EnergySourceDTO bdto = new EnergySourceDTO();
				bdto.setConsumption(response.getConsumption());
				bdto.setEndDate(LocalDate.parse(response.getEndDate()));
				bdto.setEnergyType(response.getEnergyType());
				bdto.setOwnerEmail(response.getOwnerEmail());
				bdto.setStartDate(LocalDate.parse(response.getStartDate()));
				model.addAttribute("energy", bdto);
				model.addAttribute("msg", response.getStatus());
				logger.info("Energy source updated for owner: " + energy.getOwnerEmail());
				return "citizen_energy_source";
			} else {
				EnergySourceDTO bdto = new EnergySourceDTO();
				String msg = "Error while updating Energy Source. Please try again later.";
				model.addAttribute("energy", bdto);
				model.addAttribute("msg", msg);
				logger.error("Energy source couldnot be updated for owner: " + energy.getOwnerEmail());
				return "citizen_energy_source";
			}

		} catch (Exception e) {

			throw new UserException("Application has encountered some issue, please register later.");
		}
	}
}
