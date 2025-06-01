package de.fh.energymanagementservice.controllers.rest;

import de.fh.energymanagementservice.models.EnergySource;
import de.fh.energymanagementservice.services.EnergySourceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/energy-source")
public class EnergySourceController {

    private final EnergySourceService energySourceService;

    public EnergySourceController(EnergySourceService energySourceService) {
        this.energySourceService = energySourceService;
    }

    // CREATE
    @PostMapping(path = {""})
    EnergySource create(@RequestBody EnergySource energySource) {
        return energySourceService.create(energySource);
    }

    // READ ALL
    @GetMapping(path = {""})
    List<EnergySource> getAll() {
        return energySourceService.getAll();
    }

    @GetMapping(path = {"/building/{id}"})
    List<EnergySource> building(@PathVariable int id) {
        return energySourceService.getByBuildingId(id);
    }

}
