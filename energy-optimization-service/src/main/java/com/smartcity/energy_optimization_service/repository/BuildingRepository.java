package com.smartcity.energy_optimization_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartcity.energy_optimization_service.domain.entity.BuildingEntity;

import java.util.List;

@Repository
public interface BuildingRepository extends JpaRepository<BuildingEntity, Long> {
    List<BuildingEntity> findByOwnerEmail(String ownerEmail);
}
