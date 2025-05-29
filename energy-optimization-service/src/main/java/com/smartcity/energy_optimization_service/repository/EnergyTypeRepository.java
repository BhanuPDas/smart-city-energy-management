package com.smartcity.energy_optimization_service.repository;

import com.smartcity.energy_optimization_service.model.EnergyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnergyTypeRepository extends JpaRepository<EnergyType, Long> {
}
