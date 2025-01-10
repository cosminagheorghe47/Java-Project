package com.example.Project.unitEndpoints;

import com.example.Project.controllers.AirportController;
import com.example.Project.model.entities.Airport;
import com.example.Project.services.AirportService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AirportControllerTests {

    private final AirportService airportService = mock(AirportService.class);
    private final AirportController airportController = new AirportController(airportService);

    @Test
    @DisplayName("Get airports by city")
    void testGetAirportsByCity() {
        String cityName = "New York";
        Airport airport = new Airport(1, "JFK", cityName, 6);

        when(airportService.findAirportsByCity(cityName)).thenReturn(List.of(airport));

        List<Airport> result = airportController.getAirportsbyCity(cityName);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(cityName, result.get(0).getCityName());
        verify(airportService, times(1)).findAirportsByCity(cityName);
    }

    @Test
    @DisplayName("Get airport by ID")
    void testGetAirportById() {
        int id = 1;
        Airport airport = new Airport(id, "JFK", "New York", 6);

        when(airportService.findAirportById(id)).thenReturn(airport);

        Airport result = airportController.getAirport(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("JFK", result.getName());
        verify(airportService, times(1)).findAirportById(id);
    }

    @Test
    @DisplayName("Get all airports")
    void testGetAllAirports() {
        Airport airport1 = new Airport(1, "JFK", "New York", 6);
        Airport airport2 = new Airport(2, "LAX", "Los Angeles", 4);

        when(airportService.getAllAirports()).thenReturn(Arrays.asList(airport1, airport2));

        List<Airport> result = airportController.getAllAirports();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("JFK", result.get(0).getName());
        assertEquals("LAX", result.get(1).getName());
        verify(airportService, times(1)).getAllAirports();
    }

    @Test
    @DisplayName("Create airport")
    void testCreateAirport() {
        Airport airport = new Airport(1, "JFK", "New York", 6);

        when(airportService.createAirport(airport)).thenReturn(airport);

        Airport result = airportController.createAirport(airport);

        assertNotNull(result);
        assertEquals("JFK", result.getName());
        verify(airportService, times(1)).createAirport(airport);
    }

    @Test
    @DisplayName("Update airport")
    void testUpdateAirport() {
        int id = 1;
        Airport existingAirport = new Airport(id, "JFK", "New York", 6);
        Airport updatedAirport = new Airport(id, "LAX", "Los Angeles", 4);

        when(airportService.updateAirport(id, updatedAirport)).thenReturn(updatedAirport);

        Airport result = airportController.updateAirport(id, updatedAirport);

        assertNotNull(result);
        assertEquals("LAX", result.getName());
        assertEquals("Los Angeles", result.getCityName());
        verify(airportService, times(1)).updateAirport(id, updatedAirport);
    }

    @Test
    @DisplayName("Delete airport")
    void testDeleteAirport() {
        int id = 1;

        doNothing().when(airportService).deleteAirport(id);

        airportController.deleteAirport(id);

        verify(airportService, times(1)).deleteAirport(id);
    }
}
