package com.fhdo.energy_analytics_service.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fhdo.energy_analytics_service.domain.entity.EnergyTypeEntity;

public interface EnergyTypeRepository extends JpaRepository<EnergyTypeEntity, Long> {
}
