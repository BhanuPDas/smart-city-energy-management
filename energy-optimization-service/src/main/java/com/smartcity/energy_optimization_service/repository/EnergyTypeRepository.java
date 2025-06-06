package com.smartcity.energy_optimization_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartcity.energy_optimization_service.domain.entity.EnergyTypeEntity;

@Repository
public interface EnergyTypeRepository extends JpaRepository<EnergyTypeEntity, Long> {
}
