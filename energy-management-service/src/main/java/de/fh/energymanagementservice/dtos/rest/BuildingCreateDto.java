package de.fh.energymanagementservice.dtos.rest;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// Using Lombok for boilerplate code (getters, setters, constructors)
public class BuildingCreateDto {

    @NotBlank(message = "City cannot be empty") // Ensures the string is not null and contains at least one non-whitespace character
    private String city;

    @NotBlank(message = "Address cannot be empty")
    private String address;

    @NotNull(message = "Zip code cannot be null") // Ensures the integer is not null
    private int zipCode;

    @NotBlank(message = "Owner email cannot be empty")
    @Email(message = "Owner email must be a valid email format") // Ensures the string is a valid email address
    private String ownerEmail;

    @NotNull(message = "Floor area cannot be null")
    @Min(value = 1, message = "Floor area must be at least 1 square meter")
    private int floorArea;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public int getFloorArea() {
        return floorArea;
    }

    public void setFloorArea(int floorArea) {
        this.floorArea = floorArea;
    }
}
