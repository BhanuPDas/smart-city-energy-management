package com.fhdo.energy_analysis_service.repository;
import com.fhdo.energy_analysis_service.model.EnergyType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnergyTypeRepository extends JpaRepository<EnergyType, Long> {
}
