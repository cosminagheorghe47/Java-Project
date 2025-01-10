package com.example.Project.unitEndpoints;

import com.example.Project.controllers.FlightController;
import com.example.Project.model.entities.Flight;
import com.example.Project.services.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FlightControllerTests {

    private FlightService flightService;
    private FlightController flightController;

    @BeforeEach
    void setUp() {
        flightService = mock(FlightService.class);
        flightController = new FlightController(flightService);
    }

    @Test
    void testAddFlight() {
        Flight flight = new Flight();
        flight.setId(1);
        flight.setCode("123");

        when(flightService.addFlight(flight)).thenReturn(flight);

        ResponseEntity<Flight> response = flightController.addFlight(flight);

        assertNotNull(response);
        assertEquals(1, response.getBody().getId());
        verify(flightService, times(1)).addFlight(flight);
    }

    @Test
    void testUpdateFlight() {
        Flight existingFlight = new Flight();
        existingFlight.setId(1);
        existingFlight.setCode("123");

        Flight updatedDetails = new Flight();
        updatedDetails.setCode("456");

        when(flightService.updateFlight(1, updatedDetails)).thenReturn(updatedDetails);

        ResponseEntity<Flight> response = flightController.updateFlight(1, updatedDetails);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("456", response.getBody().getCode());
        verify(flightService, times(1)).updateFlight(1, updatedDetails);
    }


    @Test
    void testDeleteFlight() {
        doNothing().when(flightService).deleteFlight(1);

        ResponseEntity<Void> response = flightController.deleteFlight(1);

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(flightService, times(1)).deleteFlight(1);
    }

    @Test
    void testSearchFlights() {
        Date arrivalDate = new Date();
        Date departureDate = new Date();
        int arrivalAirport = 2;
        int departureAirport = 1;

        Flight flight = new Flight();
        flight.setId(1);

        when(flightService.searchFlights(arrivalDate, departureDate, arrivalAirport, departureAirport))
                .thenReturn(Arrays.asList(flight));

        ResponseEntity<List<Flight>> response = flightController.searchFlights(arrivalDate, departureDate, arrivalAirport, departureAirport);

        assertNotNull(response);
        assertEquals(1, response.getBody().size());
        verify(flightService, times(1)).searchFlights(arrivalDate, departureDate, arrivalAirport, departureAirport);
    }

    @Test
    void testGetAllFlights() {
        Flight flight1 = new Flight();
        flight1.setId(1);

        Flight flight2 = new Flight();
        flight2.setId(2);

        when(flightService.getAllFlights()).thenReturn(Arrays.asList(flight1, flight2));

        ResponseEntity<List<Flight>> response = flightController.getAllFlights();

        assertNotNull(response);
        assertEquals(2, response.getBody().size());
        verify(flightService, times(1)).getAllFlights();
    }
}
