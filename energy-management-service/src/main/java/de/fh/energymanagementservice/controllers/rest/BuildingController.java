package de.fh.energymanagementservice.controllers.rest;

import de.fh.energymanagementservice.dtos.rest.BuildingCreateDto;
import de.fh.energymanagementservice.models.Building;
import de.fh.energymanagementservice.services.BuildingService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/building")
public class BuildingController {

    private final BuildingService buildingService;

    public BuildingController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    // CREATE
    @PostMapping(path = {""})
    Building create(@Valid @RequestBody BuildingCreateDto buildingCreateDto) {
        return buildingService.create(
                buildingService.convertDtoToEntity(buildingCreateDto)
        );
    }

    // READ ALL
    @GetMapping(path = {""})
    List<Building> getAll() {
        return buildingService.getAll();
    }

    // READ
    @GetMapping(path = {"/{id}"})
    Building get(@PathVariable int id) {
        return buildingService.getById(id);
    }

    // UPDATE
    @PutMapping(path = {"/{id}"})
    Building update(@PathVariable int id, @RequestBody Building building) {
        Building button = buildingService.getById(id);
        if (button != null) {
            return buildingService.update(building);
        }
        return null;
    }

    // READ
    @GetMapping(path = {"/zipcode/{code}"})
    List<Building> getByZipcode(@PathVariable int code) {
        return buildingService.getByZipcode(code);
    }

    // READ
    @GetMapping(path = {"/owner/{email}"})
    Building getByEmail(@PathVariable String email) {
        return buildingService.getByEmail(email);
    }

    // DELETE
    @DeleteMapping(path = {"/{id}"})
    void delete(@PathVariable int id) {
        buildingService.deleteById(id);
    }
}
