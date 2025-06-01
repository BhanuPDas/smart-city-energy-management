package com.fhdo.energy_analytics_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fhdo.energy_analytics_service.domain.entity.EnergySourceEntity;

import java.time.LocalDate;
import java.util.List;

public interface EnergySourceRepository extends JpaRepository<EnergySourceEntity, Long> {
	List<EnergySourceEntity> findByBuildingId(Long buildingId);

	@Query("SELECT e FROM EnergySourceEntity e " + "WHERE e.building.id = :buildingIds "
			+ "AND e.startDate >= :startDate " + "AND e.endDate <= CURRENT_DATE")
	List<EnergySourceEntity> findByBuildingIdsAndStartDate(@Param("buildingIds") Long buildingIds,
			@Param("startDate") LocalDate startDate);

	@Query("SELECT e FROM EnergySourceEntity e " + "WHERE e.building.id IN :buildingIds "
			+ "AND e.startDate >= :startDate " + "AND e.endDate <= CURRENT_DATE")
	List<EnergySourceEntity> findByBuildingIdsAndStartDate(@Param("buildingIds") List<Long> buildingIds,
			@Param("startDate") LocalDate startDate);
}
