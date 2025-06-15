package de.fh.energymanagementservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import de.fh.energymanagementservice.domain.entity.EnergyTypeEntity;

@Repository
public interface EnergyTypeRepository extends JpaRepository<EnergyTypeEntity, Long> {
	Optional<EnergyTypeEntity> findByName(String name);
}
