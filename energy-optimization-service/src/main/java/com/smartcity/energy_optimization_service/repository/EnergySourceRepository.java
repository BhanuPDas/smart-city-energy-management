package com.smartcity.energy_optimization_service.repository;

import com.smartcity.energy_optimization_service.model.Building;
import com.smartcity.energy_optimization_service.model.EnergySource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnergySourceRepository extends JpaRepository<EnergySource, Long> {
    List<EnergySource> findByBuilding(Building building);
}
