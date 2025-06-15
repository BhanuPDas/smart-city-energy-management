package de.fh.energymanagementservice.services;

import de.fh.energymanagementservice.domain.request.BuildingEnergyRequest;
import de.fh.energymanagementservice.domain.request.EnergySourceRequest;
import de.fh.energymanagementservice.domain.response.BuildingEnergyResponse;
import de.fh.energymanagementservice.domain.response.EnergySourceResponse;

public interface EnergyManagementService {

	public BuildingEnergyResponse addBuildingEnergy(BuildingEnergyRequest request);

	public BuildingEnergyResponse modifyBuildingEnergy(BuildingEnergyRequest request);

	public EnergySourceResponse addEnergySource(EnergySourceRequest request);

	public EnergySourceResponse updateEnergySource(EnergySourceRequest request);
}
