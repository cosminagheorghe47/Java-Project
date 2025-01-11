package com.example.Project.controllers;

import com.example.Project.model.entities.Airport;
import com.example.Project.services.AirportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/airport")
@Tag(name = "Airport", description = "Operations related to Airports")
public class AirportController {
    private final AirportService airportService;

    @Operation(summary = "Retrieve airports by city",
            description = "Fetches a list of airports based on the provided city name.")
    @GetMapping("/city/{name}")
    public List<Airport> getAirportsbyCity(@PathVariable final String name) {
        return airportService.findAirportsByCity(name);
    }

    @Operation(summary = "Retrieve airport details",
            description = "Fetches the details of an airport by its ID.")
    @GetMapping("/{id}")
    public Airport getAirport(@PathVariable final int id) {
        return airportService.findAirportById(id);
    }

    @Operation(summary = "Retrieve all airports",
            description = "Fetches a list of all airports.")
    @GetMapping("/")
    public List<Airport> getAllAirports() {
        return airportService.getAllAirports();
    }

    @Operation(summary = "Create a new airport",
            description = "Creates a new airport using the provided details.")
    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public Airport createAirport(@RequestBody @Valid Airport airport) {
        return airportService.createAirport(airport);
    }

    @Operation(summary = "Update airport details",
            description = "Updates the details of an existing airport based on its ID.")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Airport updateAirport(@PathVariable final int id,
                                 @RequestBody @Valid Airport airport) {
        return airportService.updateAirport(id, airport);
    }

    @Operation(summary = "Delete an airport",
            description = "Deletes an airport by its ID.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAirport(@PathVariable final int id) {
        airportService.deleteAirport(id);
    }
}
