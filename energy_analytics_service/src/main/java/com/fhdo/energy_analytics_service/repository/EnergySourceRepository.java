package com.fhdo.energy_analytics_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fhdo.energy_analytics_service.domain.entity.EnergySourceEntity;

import java.util.List;

public interface EnergySourceRepository extends JpaRepository<EnergySourceEntity, Long> {
    List<EnergySourceEntity> findByBuildingId(Long buildingId);
}
