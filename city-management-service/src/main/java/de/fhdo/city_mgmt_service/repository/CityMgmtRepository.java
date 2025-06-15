package de.fhdo.city_mgmt_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.fhdo.city_mgmt_service.domain.entity.CityMgmtEntity;

@Repository
public interface CityMgmtRepository extends JpaRepository<CityMgmtEntity, Integer>{

}
