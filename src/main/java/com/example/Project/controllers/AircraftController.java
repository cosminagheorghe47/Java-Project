package com.example.Project.controllers;

import com.example.Project.model.entities.Aircraft;
import com.example.Project.model.entities.Airport;
import com.example.Project.services.AircraftService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
    public Aircraft createAircraft(@RequestBody  final Aircraft aircraft) {
        return aircraftService.createAircraft(aircraft);
    }

    @PutMapping("/{id}")
    public Aircraft updateAircraft(@PathVariable final int id,@RequestBody  final Aircraft aircraft) {
        return aircraftService.updateAircraft(id, aircraft);
    }
    @DeleteMapping("/{id}")
    public void deleteAircraft(@PathVariable final int id) {
         aircraftService.deleteAircraft(id);
    }
}
