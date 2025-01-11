package com.example.Project.controllers;

import com.example.Project.model.entities.Flight;
import com.example.Project.services.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/flights")
@Tag(name = "Flight", description = "Operations for managing flights")
public class FlightController {

    private final FlightService flightService;

    @Operation(summary = "Add a new flight",
            description = "Allows an admin to add a new flight to the system.")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Flight> addFlight(@RequestBody Flight flight) {
        return ResponseEntity.ok(flightService.addFlight(flight));
    }

    @Operation(summary = "Update flight details",
            description = "Allows an admin to update flight details based on the flight ID.")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Flight> updateFlight(@PathVariable Integer id, @RequestBody Flight flightDetails) {
        try {
            Flight updatedFlight = flightService.updateFlight(id, flightDetails);
            return ResponseEntity.ok(updatedFlight);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @Operation(summary = "Delete a flight",
            description = "Allows an admin to delete a flight by its ID.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteFlight(@PathVariable Integer id) {
        flightService.deleteFlight(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Search for flights",
            description = "Allows clients to search for flights based on various criteria such as arrival date, departure date, and airports.")
    @GetMapping("/search")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<Flight>> searchFlights(
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date arrivalDate,

            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date departureDate,

            @RequestParam(required = false) Integer arrivalAirport,
            @RequestParam(required = false) Integer departureAirport) {

        return ResponseEntity.ok(flightService.searchFlights(arrivalDate, departureDate, arrivalAirport, departureAirport));
    }

    @Operation(summary = "Get a list of all flights",
            description = "Fetches all flights from the system. Clients can access this.")
    @GetMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<Flight>> getAllFlights() {
        return ResponseEntity.ok(flightService.getAllFlights());
    }
}
