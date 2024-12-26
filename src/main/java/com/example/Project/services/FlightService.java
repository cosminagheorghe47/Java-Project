package com.example.Project.services;

import com.example.Project.model.entities.Flight;
import com.example.Project.repositories.FlightRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;

    public Flight addFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    public Flight updateFlight(Integer id, Flight flightDetails) {
        Optional<Flight> existingFlight = flightRepository.findById(id);
        if (existingFlight.isPresent()) {
            Flight flight = existingFlight.get();
            flight.setCode(flightDetails.getCode());
            flight.setDepartureDate(flightDetails.getDepartureDate());
            flight.setArrivalDate(flightDetails.getArrivalDate());
            flight.setDepartureAirport(flightDetails.getDepartureAirport());
            flight.setArrivalAirport(flightDetails.getArrivalAirport());
            flight.setPilot(flightDetails.getPilot());
            flight.setDistance(flightDetails.getDistance());
            flight.setSoldSeatsEconomy(flightDetails.getSoldSeatsEconomy());
            flight.setSoldSeatsFirstClass(flightDetails.getSoldSeatsFirstClass());
            flight.setAircraft(flightDetails.getAircraft());
            return flightRepository.save(flight);
        }
        throw new RuntimeException("Flight not found with ID: " + id);
    }

    public void deleteFlight(Integer id) {
        flightRepository.deleteById(id);
    }

    public List<Flight> searchFlights(Date arrivalDate, Date departureDate, Integer arrivalAirport, Integer departureAirport) {
        return flightRepository.searchFlights(arrivalDate, departureDate, arrivalAirport, departureAirport);
    }

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }
}
