package com.fhdo.energy_analytics_service.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fhdo.energy_analytics_service.domain.entity.BuildingEntity;

import java.util.List;
import java.util.Optional;

public interface BuildingRepository extends JpaRepository<BuildingEntity, Long> {
    Optional<BuildingEntity> findByOwnerEmail(String ownerEmail);
    List<BuildingEntity> findByZipCode(Integer zipCode);
    List<BuildingEntity> findByCity(String city);
}

