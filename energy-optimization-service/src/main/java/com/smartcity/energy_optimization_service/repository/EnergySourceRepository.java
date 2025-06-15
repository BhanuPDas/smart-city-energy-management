package com.smartcity.energy_optimization_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartcity.energy_optimization_service.domain.entity.BuildingEntity;
import com.smartcity.energy_optimization_service.domain.entity.EnergySourceEntity;

import java.util.List;

@Repository
public interface EnergySourceRepository extends JpaRepository<EnergySourceEntity, Long> {
    List<EnergySourceEntity> findByBuilding(BuildingEntity building);
}
