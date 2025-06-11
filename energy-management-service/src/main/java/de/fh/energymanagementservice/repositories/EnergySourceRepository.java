package de.fh.energymanagementservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import de.fh.energymanagementservice.domain.entity.EnergySourceEntity;

import java.util.List;

@Repository
public interface EnergySourceRepository extends JpaRepository<EnergySourceEntity, Long> {
	List<EnergySourceEntity> findByBuildingId(Long building_id);
}
