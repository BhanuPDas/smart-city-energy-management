package de.fh.energymanagementservice.repositories;

import de.fh.energymanagementservice.models.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Integer> {
    Optional<List<Building>> findByZipCode(int zipCode);
    Optional<Building> findByOwnerEmail(String email);
    Optional <Building> findById(Integer id);
}
