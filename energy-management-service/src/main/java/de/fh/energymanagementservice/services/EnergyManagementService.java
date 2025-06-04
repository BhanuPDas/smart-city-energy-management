package de.fh.energymanagementservice.services;

import de.fh.energymanagementservice.domain.request.BuildingEnergyRequest;
import de.fh.energymanagementservice.domain.response.BuildingEnergyResponse;

public interface EnergyManagementService {

	public BuildingEnergyResponse addBuildingEnergy(BuildingEnergyRequest request);

	public BuildingEnergyResponse modifyBuildingEnergy(BuildingEnergyRequest request);
}
