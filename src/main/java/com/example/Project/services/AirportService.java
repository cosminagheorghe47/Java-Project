package com.example.Project.services;

import com.example.Project.model.entities.Airport;
import com.example.Project.repositories.AirportRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class AirportService {
    private final AirportRepository airportRepository;

    public List<Airport> findAirportsByCity(final String name) {
        return airportRepository.findAirportsByCity(name)
                .orElseThrow();
    }
    public Airport findAirportById (final int id){
        return airportRepository.findById(id)
                .orElseThrow();
    }
    public List<Airport> getAllAirports() {
        return airportRepository.findAll();
    }
    public Airport createAirport(final Airport airport) {
        return airportRepository.save(airport);
    }
    public Airport updateAirport(final int id, final Airport airport) {
        Airport existingAirport = airportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Airport not found"));


        existingAirport.setName(airport.getName());
        existingAirport.setCityName(airport.getCityName());
        existingAirport.setNrOfTerminals(airport.getNrOfTerminals());

        return airportRepository.save(airport);
    }
    public void deleteAirport(final int id) {
        airportRepository.deleteById(id);
    }


}
