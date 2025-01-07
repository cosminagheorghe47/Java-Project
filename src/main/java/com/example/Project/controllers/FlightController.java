package com.example.Project.controllers;


import com.example.Project.model.entities.Flight;
import com.example.Project.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Flight> addFlight(@RequestBody Flight flight) {
        return ResponseEntity.ok(flightService.addFlight(flight));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Flight> updateFlight(@PathVariable Integer id, @RequestBody Flight flightDetails) {
        return ResponseEntity.ok(flightService.updateFlight(id, flightDetails));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteFlight(@PathVariable Integer id) {
        flightService.deleteFlight(id);
        return ResponseEntity.noContent().build();
    }

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


    @GetMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<List<Flight>> getAllFlights() {
        return ResponseEntity.ok(flightService.getAllFlights());
    }
}
