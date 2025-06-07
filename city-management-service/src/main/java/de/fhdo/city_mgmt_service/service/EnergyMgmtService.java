package de.fhdo.city_mgmt_service.service;

import de.fhdo.city_mgmt_service.domain.request.EnergyMgmtRequest;
import de.fhdo.city_mgmt_service.domain.request.EnergySourceRequest;
import de.fhdo.city_mgmt_service.domain.response.EnergyMgmtResponse;
import de.fhdo.city_mgmt_service.domain.response.EnergySourceResponse;
import de.fhdo.city_mgmt_service.exception.UserException;

public interface EnergyMgmtService {

	public EnergyMgmtResponse addBuildingEnergy(EnergyMgmtRequest request) throws UserException;

	public EnergyMgmtResponse updateBuildingEnergy(EnergyMgmtRequest request) throws UserException;

	public EnergySourceResponse addResidentEnergy(EnergySourceRequest request) throws UserException;

	public EnergySourceResponse updateResidentEnergy(EnergySourceRequest request) throws UserException;
}
