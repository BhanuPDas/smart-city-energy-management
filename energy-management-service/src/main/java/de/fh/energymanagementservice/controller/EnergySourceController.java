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

import de.fh.energymanagementservice.domain.request.EnergySourceRequest;
import de.fh.energymanagementservice.domain.response.EnergySourceResponse;
import de.fh.energymanagementservice.services.EnergyManagementService;

@RestController
@RequestMapping("/api/v1/energy-source")
public class EnergySourceController {

	private final Logger logger = LoggerFactory.getLogger(EnergySourceController.class);
	@Autowired
	private EnergyManagementService service;

	@PostMapping("/add")
	public ResponseEntity<EnergySourceResponse> addEnergySource(@RequestBody EnergySourceRequest request) {
		if (request != null) {
			logger.info("Adding Energy Source details for: owner: " + request.getOwnerEmail());
			return ResponseEntity.ok(service.addEnergySource(request));
		} else {
			logger.info("Empty Request received for adding energy source");
			return ResponseEntity.ok(null);
		}
	}

	@PutMapping("/update")
	public ResponseEntity<EnergySourceResponse> updateEnergySource(@RequestBody EnergySourceRequest request) {
		if (request != null) {
			logger.info("Updating Energy Source details for: owner: " + request.getOwnerEmail());
			return ResponseEntity.ok(service.updateEnergySource(request));
		} else {
			logger.info("Empty Request received for updating energy source");
			return ResponseEntity.ok(null);
		}
	}
}
