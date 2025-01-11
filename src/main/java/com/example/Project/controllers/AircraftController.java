package com.example.Project.controllers;

import com.example.Project.model.entities.Aircraft;
import com.example.Project.services.AircraftService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/aircraft")
@Tag(name = "Aircraft", description = "Operations related to Aircraft")
public class AircraftController {
    private final AircraftService aircraftService;

    @Operation(summary = "Retrieve aircrafts by name",
            description = "Fetches a list of aircrafts based on the provided name.")
    @GetMapping("/name/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Aircraft> getAircrafts(@Parameter(description = "Name of the aircraft.", required = true)
                                       @PathVariable final String name) {
        return aircraftService.findAircraftByName(name);
    }

    @Operation(summary = "Retrieve aircraft details",
            description = "Fetches the details of an aircraft by its ID.")
    @GetMapping("/{id}")
    public Aircraft getAircraft(@Parameter(description = "ID of the aircraft.", required = true)
                                @PathVariable final int id) {
        return aircraftService.findAircraftById(id);
    }

    @Operation(summary = "Retrieve all aircrafts",
            description = "Fetches a list of all aircrafts.")
    @GetMapping("/")
    public List<Aircraft> getAllAircrafts() {
        return aircraftService.getAllAircrafts();
    }

    @Operation(summary = "Create a new aircraft",
            description = "Creates a new aircraft using the provided details.")
    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public Aircraft createAircraft(@RequestBody @Valid Aircraft aircraft) {
        return aircraftService.createAircraft(aircraft);
    }

    @Operation(summary = "Update aircraft details",
            description = "Updates the details of an existing aircraft based on its ID.")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Aircraft updateAircraft(@Parameter(description = "ID of the aircraft to be updated.", required = true)
                                   @PathVariable final int id,
                                   @RequestBody @Valid Aircraft aircraft) {
        return aircraftService.updateAircraft(id, aircraft);
    }

    @Operation(summary = "Delete an aircraft",
            description = "Deletes an aircraft by its ID.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAircraft(@Parameter(description = "ID of the aircraft to be deleted.", required = true)
                               @PathVariable final int id) {
        aircraftService.deleteAircraft(id);
    }
}
