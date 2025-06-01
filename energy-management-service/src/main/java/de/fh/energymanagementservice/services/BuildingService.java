package de.fh.energymanagementservice.services;

import de.fh.energymanagementservice.dtos.rest.BuildingCreateDto;
import de.fh.energymanagementservice.models.Building;
import de.fh.energymanagementservice.repositories.BuildingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildingService {

    @Autowired
    private BuildingRepository buildingRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    public Building create(Building building) {
        return buildingRepository.save(building);
    }

    public Building update(Building building) {
        return buildingRepository.save(building);
    }

    public List<Building> getAll() {
        return buildingRepository.findAll();
    }

    public Building getById(int id) {
        return buildingRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Building with id " + id + " does not exist")
        );
    }

    public List<Building> getByZipcode(int zipcode) {
        return buildingRepository.findByZipCode(zipcode).orElseThrow(
                () -> new IllegalArgumentException("No building found for zipcode: " + zipcode)
        );
    }

    public Building getByEmail(String email) {
        return buildingRepository.findByOwnerEmail(email).orElseThrow(
                () -> new IllegalArgumentException("No building found for email: " + email)
        );
    }

    public void deleteById(int id) {
        buildingRepository.deleteById(id);
    }

    public Building convertDtoToEntity(BuildingCreateDto buildingCreateDto) {
//        System.out.println(buildingDto);
        return modelMapper.map(buildingCreateDto, Building.class);
    }

}
