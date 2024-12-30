package com.example.Project.unitService;

import com.example.Project.model.entities.Aircraft;
import com.example.Project.model.entities.Airport;
import com.example.Project.model.entities.Flight;
import com.example.Project.model.entities.Person;
import com.example.Project.repositories.FlightRepository;
import com.example.Project.services.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FlightServiceTests {

    private FlightRepository flightRepository;
    private FlightService flightService;

    @BeforeEach
    void setUp() {
        flightRepository = mock(FlightRepository.class);
        flightService = new FlightService(flightRepository);
    }

    @Test
    void testAddFlight() {
        Flight flight = new Flight();
        flight.setId(1);
        flight.setCode("123");

        when(flightRepository.save(flight)).thenReturn(flight);

        Flight result = flightService.addFlight(flight);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(flightRepository, times(1)).save(flight);
    }

    @Test
    void testUpdateFlight() {
        Flight existingFlight = new Flight();
        existingFlight.setId(1);
        existingFlight.setCode("123");

        Flight updatedDetails = new Flight();
        updatedDetails.setCode("456");

        when(flightRepository.findById(1)).thenReturn(Optional.of(existingFlight));
        when(flightRepository.save(existingFlight)).thenReturn(existingFlight);

        Flight result = flightService.updateFlight(1, updatedDetails);

        assertNotNull(result);
        assertEquals("456", result.getCode());
        verify(flightRepository, times(1)).findById(1);
        verify(flightRepository, times(1)).save(existingFlight);
    }

    @Test
    void testUpdateFlight_NotFound() {
        when(flightRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> flightService.updateFlight(1, new Flight()));
        verify(flightRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteFlight() {
        flightService.deleteFlight(1);

        verify(flightRepository, times(1)).deleteById(1);
    }

    @Test
    void testSearchFlights() {
        Date arrivalDate = new Date();
        Date departureDate = new Date();
        int arrivalAirport = 2;
        int departureAirport = 1;

        Flight flight = new Flight();
        flight.setId(1);

        when(flightRepository.searchFlights(arrivalDate, departureDate, arrivalAirport, departureAirport))
                .thenReturn(Arrays.asList(flight));

        List<Flight> result = flightService.searchFlights(arrivalDate, departureDate, arrivalAirport, departureAirport);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(flightRepository, times(1)).searchFlights(arrivalDate, departureDate, arrivalAirport, departureAirport);
    }

    @Test
    void testGetAllFlights() {
        Flight flight1 = new Flight();
        flight1.setId(1);

        Flight flight2 = new Flight();
        flight2.setId(2);

        when(flightRepository.findAll()).thenReturn(Arrays.asList(flight1, flight2));

        List<Flight> result = flightService.getAllFlights();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(flightRepository, times(1)).findAll();
    }
}
