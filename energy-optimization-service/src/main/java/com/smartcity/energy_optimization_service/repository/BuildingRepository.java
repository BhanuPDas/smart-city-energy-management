package com.smartcity.energy_optimization_service.repository;

import com.smartcity.energy_optimization_service.model.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {
    List<Building> findByOwnerEmail(String ownerEmail);
}
