package com.example.Project.controllers;

import com.example.Project.model.entities.Aircraft;
import com.example.Project.model.entities.Airport;
import com.example.Project.services.AircraftService;
import com.example.Project.services.AirportService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/airport")
//@Validated
public class AirportController {
    private final AirportService airportService;

    @GetMapping("/city/{name}")
    public List<Airport> getAirportsbyCity(@PathVariable final String name) {
        return airportService.findAirportsByCity(name);
    }
    @GetMapping("/{id}")
    public Airport getAirport(@PathVariable final int id) {
        return airportService.findAirportById(id);
    }

    @GetMapping("/")
    public List<Airport> getAllAirports() {
        return airportService.getAllAirports();
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public Airport createAirport(@RequestBody  final Airport airport) {
        return airportService.createAirport(airport);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Airport updateAirport(@PathVariable final int id,@RequestBody  final Airport airport) {
        return airportService.updateAirport(id, airport);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAirport(@PathVariable final int id) {
        airportService.deleteAirport(id);
    }
}
