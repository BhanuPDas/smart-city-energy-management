package de.fh.energymanagementservice.services;

import de.fh.energymanagementservice.models.Building;
import de.fh.energymanagementservice.models.EnergySource;
import de.fh.energymanagementservice.models.EnergyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import de.fh.energymanagementservice.repositories.EnergySourceRepository;

import java.util.List;

@Service
public class  EnergySourceService {

    @Autowired
    EnergySourceRepository energySourceRepository;

    public EnergySource create(EnergySource energySource) {
        return energySourceRepository.save(energySource);
    }

    public EnergySource update(EnergySource energySource) {
        return energySourceRepository.save(energySource);
    }

    public List<EnergySource> getAll() {
        return energySourceRepository.findAll();
    }

    public List<EnergySource> getByBuildingId (long id) {
        return energySourceRepository.findByBuildingId(id).orElseThrow(
                () -> new RuntimeException("No energy Source found"));
    }

    public List<EnergySource> getByEnergyType(EnergyType energyType) {
        return energySourceRepository.findByEnergyType(energyType).orElseThrow(
                () -> new RuntimeException("No energy Source found"));
    }

    void delete(EnergySource energySource) {
        energySourceRepository.delete(energySource);
    }



}
