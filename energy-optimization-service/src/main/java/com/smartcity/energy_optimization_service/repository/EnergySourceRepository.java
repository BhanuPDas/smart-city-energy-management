package com.smartcity.energy_optimization_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartcity.energy_optimization_service.domain.entity.Building;
import com.smartcity.energy_optimization_service.domain.entity.EnergySource;

import java.util.List;

@Repository
public interface EnergySourceRepository extends JpaRepository<EnergySource, Long> {
    List<EnergySource> findByBuilding(Building building);
}
