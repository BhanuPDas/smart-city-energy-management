package com.fhdo.energy_analysis_service.repository;
import com.fhdo.energy_analysis_service.model.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BuildingRepository extends JpaRepository<Building, Long> {
    Optional<Building> findByOwnerEmail(String ownerEmail);
    List<Building> findByZipCode(Integer zipCode);
    List<Building> findByCity(String city);
}

