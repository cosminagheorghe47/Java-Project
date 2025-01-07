package com.example.Project.controllers;

import com.example.Project.model.entities.Aircraft;
import com.example.Project.model.entities.Airport;
import com.example.Project.services.AircraftService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/aircraft")
//@Validated
public class AircraftController {
    private final AircraftService aircraftService;

    @GetMapping("/name/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Aircraft> getAircrafts(@PathVariable final String name) {
        return aircraftService.findAircraftByName(name);
    }
    @GetMapping("/{id}")
    public Aircraft getAircraft(@PathVariable final int id) {
        return aircraftService.findAircraftById(id);
    }
    @GetMapping("/")
    public List<Aircraft> getAllAircrafts() {
        return aircraftService.getAllAircrafts();
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public Aircraft createAircraft(@RequestBody  final Aircraft aircraft) {
        return aircraftService.createAircraft(aircraft);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Aircraft updateAircraft(@PathVariable final int id,@RequestBody  final Aircraft aircraft) {
        return aircraftService.updateAircraft(id, aircraft);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAircraft(@PathVariable final int id) {
         aircraftService.deleteAircraft(id);
    }
}
