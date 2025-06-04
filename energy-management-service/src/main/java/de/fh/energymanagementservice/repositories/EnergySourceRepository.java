package de.fh.energymanagementservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.fh.energymanagementservice.domain.entity.BuildingEntity;
import de.fh.energymanagementservice.domain.entity.EnergySourceEntity;
import de.fh.energymanagementservice.domain.entity.EnergyTypeEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnergySourceRepository extends JpaRepository<EnergySourceEntity, Long> {
	Optional<List<EnergySourceEntity>> findByBuildingId(Long building_id);

	Optional<List<EnergySourceEntity>> findByBuildingAndEnergyType(BuildingEntity building,
			EnergyTypeEntity entityType);
}
