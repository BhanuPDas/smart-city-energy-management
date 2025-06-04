package de.fh.energymanagementservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import de.fh.energymanagementservice.domain.request.BuildingEnergyRequest;
import de.fh.energymanagementservice.domain.response.BuildingEnergyResponse;
import de.fh.energymanagementservice.services.EnergyManagementService;

@RestController
@RequestMapping("/api/v1/resident-energy")
public class BuildingEnergyController {

	private final Logger logger = LoggerFactory.getLogger(BuildingEnergyController.class);
	@Autowired
	private EnergyManagementService service;

	@PostMapping("/add")
	public ResponseEntity<BuildingEnergyResponse> addBuildingEnergy(@RequestBody BuildingEnergyRequest request) {
		if (request != null) {
			logger.info("Adding Energy details for: owner: " + request.getOwnerEmail() + " & Address: "
					+ request.getAddress());
			return ResponseEntity.ok(service.addBuildingEnergy(request));
		} else {
			logger.info("Empty Request received for adding building energy");
			return ResponseEntity.ok(null);
		}
	}

	@PutMapping("/update")
	public ResponseEntity<BuildingEnergyResponse> modifyBuildingEnergy(@RequestBody BuildingEnergyRequest request) {

		if (request != null) {
			logger.info("Updating Energy details for: owner: " + request.getOwnerEmail() + " & Address: "
					+ request.getAddress());
			return ResponseEntity.ok(service.modifyBuildingEnergy(request));
		} else {
			logger.info("Empty Request received for updating building energy");
			return ResponseEntity.ok(null);
		}
	}
}
