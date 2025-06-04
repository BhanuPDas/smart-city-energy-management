package de.fh.energymanagementservice.services.impl;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import de.fh.energymanagementservice.domain.entity.BuildingEntity;
import de.fh.energymanagementservice.domain.entity.EnergySourceEntity;
import de.fh.energymanagementservice.domain.entity.EnergyTypeEntity;
import de.fh.energymanagementservice.domain.request.BuildingEnergyRequest;
import de.fh.energymanagementservice.domain.request.EnergySourceRequest;
import de.fh.energymanagementservice.domain.response.BuildingEnergyResponse;
import de.fh.energymanagementservice.domain.response.EnergySourceResponse;
import de.fh.energymanagementservice.repositories.BuildingRepository;
import de.fh.energymanagementservice.repositories.EnergySourceRepository;
import de.fh.energymanagementservice.repositories.EnergyTypeRepository;
import de.fh.energymanagementservice.services.EnergyManagementService;

@Service
public class EnergyManagementServiceImpl implements EnergyManagementService {

	private final Logger logger = LoggerFactory.getLogger(EnergyManagementServiceImpl.class);
	@Autowired
	private BuildingRepository brepo;
	@Autowired
	private EnergySourceRepository sourceRepo;
	@Autowired
	private EnergyTypeRepository typeRepo;

	public BuildingEnergyResponse addBuildingEnergy(BuildingEnergyRequest request) {
		if (request != null) {
			logger.info("Saving building and heating details for: " + request.getOwnerEmail());
			try {
				// Unique Email Id is saved and can't be modified.
				Optional<BuildingEntity> bEnt = brepo.findByOwnerEmail(request.getOwnerEmail());
				if (bEnt.isEmpty()) {
					BuildingEntity bEntity = new BuildingEntity();
					bEntity.setAddress(request.getAddress());
					bEntity.setCity(request.getCity());
					bEntity.setFloorArea(request.getFloorArea());
					bEntity.setOwnerEmail(request.getOwnerEmail());
					bEntity.setZipCode(request.getZipCode());
					bEntity = brepo.save(bEntity);
					EnergyTypeEntity typeEntity = new EnergyTypeEntity();
					typeEntity = typeRepo.findByName(request.getEnergyType()).get();
					EnergySourceEntity sourceEntity = new EnergySourceEntity();
					sourceEntity.setBuilding(bEntity);
					sourceEntity.setConsumption(request.getConsumption());
					sourceEntity.setStartDate(LocalDate.parse(request.getStartDate()));
					sourceEntity.setEndDate(LocalDate.parse(request.getEndDate()));
					sourceEntity.setEnergyType(typeEntity);
					sourceEntity = sourceRepo.save(sourceEntity);
					BuildingEnergyResponse response = new BuildingEnergyResponse();
					response.setAddress(sourceEntity.getBuilding().getAddress());
					response.setCity(sourceEntity.getBuilding().getCity());
					response.setConsumption(sourceEntity.getConsumption());
					response.setEndDate(sourceEntity.getEndDate());
					response.setEnergyType(sourceEntity.getEnergyType().getName());
					response.setFloorArea(sourceEntity.getBuilding().getFloorArea());
					response.setOwnerEmail(sourceEntity.getBuilding().getOwnerEmail());
					response.setStartDate(sourceEntity.getStartDate());
					response.setZipCode(sourceEntity.getBuilding().getZipCode());
					response.setStatus("Building and Energy Source registered successfully.");
					return response;
				} else {
					logger.error("Resident address is already registered with the email id. Can not be added again. "
							+ request.getOwnerEmail());
				}
			} catch (Exception e) {
				logger.error("Exception occurred while persisting building and heating details" + e.getMessage());
			}
		}

		return null;
	}

	public BuildingEnergyResponse modifyBuildingEnergy(BuildingEnergyRequest request) {

		if (request != null) {
			logger.info("Modifying building and heating details for: " + request.getOwnerEmail());
			try {
				// Unique Email Id is saved and can't be modified.
				Optional<BuildingEntity> bEnt = brepo.findByOwnerEmail(request.getOwnerEmail());
				if (bEnt.isPresent()) {
					BuildingEntity bEntity = new BuildingEntity();
					bEntity.setId(bEnt.get().getId());
					bEntity.setAddress(request.getAddress());
					bEntity.setCity(request.getCity());
					bEntity.setFloorArea(request.getFloorArea());
					bEntity.setZipCode(request.getZipCode());
					bEntity.setOwnerEmail(bEnt.get().getOwnerEmail());
					bEntity = brepo.save(bEntity);
					EnergyTypeEntity typeEntity = new EnergyTypeEntity();
					typeEntity = typeRepo.findByName(request.getEnergyType()).get();
					Optional<List<EnergySourceEntity>> sEntity = sourceRepo.findByBuildingAndEnergyType(bEntity,
							typeEntity);
					if (sEntity.isPresent()) {
						List<EnergySourceEntity> esourceList = sEntity.get();
						esourceList.sort(Comparator.comparing(EnergySourceEntity::getId).reversed());
						EnergySourceEntity sourceEntity = new EnergySourceEntity();
						sourceEntity.setId(esourceList.get(0).getId());
						sourceEntity.setBuilding(bEntity);
						sourceEntity.setConsumption(request.getConsumption());
						sourceEntity.setStartDate(LocalDate.parse(request.getStartDate()));
						sourceEntity.setEndDate(LocalDate.parse(request.getEndDate()));
						sourceEntity.setEnergyType(typeEntity);
						sourceEntity = sourceRepo.save(sourceEntity);
						BuildingEnergyResponse response = new BuildingEnergyResponse();
						response.setAddress(sourceEntity.getBuilding().getAddress());
						response.setCity(sourceEntity.getBuilding().getCity());
						response.setConsumption(sourceEntity.getConsumption());
						response.setEndDate(sourceEntity.getEndDate());
						response.setEnergyType(sourceEntity.getEnergyType().getName());
						response.setFloorArea(sourceEntity.getBuilding().getFloorArea());
						response.setOwnerEmail(sourceEntity.getBuilding().getOwnerEmail());
						response.setStartDate(sourceEntity.getStartDate());
						response.setZipCode(sourceEntity.getBuilding().getZipCode());
						response.setStatus("Building and Energy Source updated successfully.");
						return response;
					}
				} else {
					logger.error("Resident Owner email is not registered." + request.getOwnerEmail());
				}
			} catch (Exception e) {
				logger.error("Exception occurred while updating building and heating details" + e.getMessage());
			}
		}

		return null;
	}

	public EnergySourceResponse addEnergySource(EnergySourceRequest request) {
		if (request != null) {
			logger.info("Saving energy source details for: " + request.getOwnerEmail());
			try {
				// Unique Email Id is saved and can't be modified.
				Optional<BuildingEntity> bEnt = brepo.findByOwnerEmail(request.getOwnerEmail());
				if (bEnt.isPresent()) {
					EnergyTypeEntity typeEntity = new EnergyTypeEntity();
					typeEntity = typeRepo.findByName(request.getEnergyType()).get();
					EnergySourceEntity sourceEntity = new EnergySourceEntity();
					sourceEntity.setBuilding(bEnt.get());
					sourceEntity.setConsumption(request.getConsumption());
					sourceEntity.setStartDate(LocalDate.parse(request.getStartDate()));
					sourceEntity.setEndDate(LocalDate.parse(request.getEndDate()));
					sourceEntity.setEnergyType(typeEntity);
					sourceEntity = sourceRepo.save(sourceEntity);
					EnergySourceResponse response = new EnergySourceResponse();
					response.setConsumption(sourceEntity.getConsumption());
					response.setEndDate(sourceEntity.getEndDate());
					response.setEnergyType(sourceEntity.getEnergyType().getName());
					response.setOwnerEmail(sourceEntity.getBuilding().getOwnerEmail());
					response.setStartDate(sourceEntity.getStartDate());
					response.setStatus("Energy Source added successfully.");
					return response;
				} else {
					logger.error("Owner email is not registered. Cannot add energy source: " + request.getOwnerEmail());
				}
			} catch (Exception e) {
				logger.error("Exception occurred while persisting energy source details" + e.getMessage());
			}
		}

		return null;
	}

	public EnergySourceResponse updateEnergySource(EnergySourceRequest request) {

		if (request != null) {
			logger.info("Updating energy source details for: " + request.getOwnerEmail());
			try {
				// Unique Email Id is saved and can't be modified.
				Optional<BuildingEntity> bEnt = brepo.findByOwnerEmail(request.getOwnerEmail());
				if (bEnt.isPresent()) {
					EnergyTypeEntity typeEntity = new EnergyTypeEntity();
					typeEntity = typeRepo.findByName(request.getEnergyType()).get();
					Optional<List<EnergySourceEntity>> sEntity = sourceRepo.findByBuildingAndEnergyType(bEnt.get(),
							typeEntity);
					if (sEntity.isPresent()) {
						List<EnergySourceEntity> esourceList = sEntity.get();
						esourceList.sort(Comparator.comparing(EnergySourceEntity::getId).reversed());
						EnergySourceEntity sourceEntity = new EnergySourceEntity();
						sourceEntity.setId(esourceList.get(0).getId());
						sourceEntity.setBuilding(bEnt.get());
						sourceEntity.setConsumption(request.getConsumption());
						sourceEntity.setStartDate(LocalDate.parse(request.getStartDate()));
						sourceEntity.setEndDate(LocalDate.parse(request.getEndDate()));
						sourceEntity.setEnergyType(typeEntity);
						sourceEntity = sourceRepo.save(sourceEntity);
						EnergySourceResponse response = new EnergySourceResponse();
						response.setConsumption(sourceEntity.getConsumption());
						response.setEndDate(sourceEntity.getEndDate());
						response.setEnergyType(sourceEntity.getEnergyType().getName());
						response.setOwnerEmail(sourceEntity.getBuilding().getOwnerEmail());
						response.setStartDate(sourceEntity.getStartDate());
						response.setStatus("Energy Source updated successfully.");
						return response;
					}
				} else {
					logger.error("Resident Owner email is not registered. Cannot update energy source: "
							+ request.getOwnerEmail());
				}
			} catch (Exception e) {
				logger.error("Exception occurred while updating energy source details" + e.getMessage());
			}
		}

		return null;
	}
}
