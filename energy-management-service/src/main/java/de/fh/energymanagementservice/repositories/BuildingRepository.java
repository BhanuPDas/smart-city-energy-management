package de.fh.energymanagementservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import de.fh.energymanagementservice.domain.entity.BuildingEntity;

@Repository
public interface BuildingRepository extends JpaRepository<BuildingEntity, Long> {
	BuildingEntity findByOwnerEmail(String email);
}
