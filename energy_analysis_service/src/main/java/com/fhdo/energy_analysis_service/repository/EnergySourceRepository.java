package com.fhdo.energy_analysis_service.repository;

import com.fhdo.energy_analysis_service.model.EnergySource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnergySourceRepository extends JpaRepository<EnergySource, Long> {
    List<EnergySource> findByBuildingId(Long buildingId);
}
