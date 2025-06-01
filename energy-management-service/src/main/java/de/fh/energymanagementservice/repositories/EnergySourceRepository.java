package de.fh.energymanagementservice.repositories;

import de.fh.energymanagementservice.models.EnergySource;
import de.fh.energymanagementservice.models.EnergyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnergySourceRepository extends JpaRepository<EnergySource, Integer> {
    Optional <List<EnergySource>> findByBuildingId(Long building_id);
    Optional <List<EnergySource>> findByEnergyType(EnergyType energyType);
}
