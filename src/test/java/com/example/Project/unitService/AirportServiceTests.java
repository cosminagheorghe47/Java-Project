package com.example.Project.unitService;

import com.example.Project.model.entities.Airport;
import com.example.Project.repositories.AirportRepository;
import com.example.Project.repositories.BookingRepository;
import com.example.Project.repositories.CouponRepository;
import com.example.Project.services.AirportService;
import com.example.Project.services.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AirportServiceTests {

    private AirportRepository airportRepository;
    private AirportService airportService ;

    @BeforeEach
    void setUp() {
        airportRepository = mock(AirportRepository.class);
        airportService = new AirportService(airportRepository);
    }

    @Test
    @DisplayName("Find airports by city - valid city")
    void testFindAirportsByCity_Valid() {
        String cityName = "New York";
        Airport airport = new Airport(1, "JFK", "New York", 6);


        when(airportRepository.findAirportsByCity(cityName))
                .thenReturn(Optional.of(Arrays.asList(airport)));

        List<Airport> result = airportService.findAirportsByCity(cityName);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(cityName, result.get(0).getCityName());
        verify(airportRepository, times(1)).findAirportsByCity(cityName);
    }

    @Test
    @DisplayName("Find airports by city - invalid city")
    void testFindAirportsByCity_Invalid() {
        String cityName = "Braila";

        when(airportRepository.findAirportsByCity(cityName))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> airportService.findAirportsByCity(cityName));
        verify(airportRepository, times(1)).findAirportsByCity(cityName);
    }

    @Test
    @DisplayName("Find airport by ID - valid ID")
    void testFindAirportById_Valid() {
        int id = 1;
        Airport airport = new Airport();
        airport.setId(id);
        airport.setName("JFK");

        when(airportRepository.findById(id))
                .thenReturn(Optional.of(airport));

        Airport result = airportService.findAirportById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(airportRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Find airport by ID - invalid ID")
    void testFindAirportById_Invalid() {
        int id = 999;

        when(airportRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> airportService.findAirportById(id));
        verify(airportRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Get all airports")
    void testGetAllAirports() {
        Airport airport1 = new Airport(1, "JFK", "New York", 6);
        Airport airport2 = new Airport(2, "LAX", "Los Angeles", 6);

        when(airportRepository.findAll())
                .thenReturn(Arrays.asList(airport1, airport2));

        List<Airport> result = airportService.getAllAirports();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(airportRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Create new airport")
    void testCreateAirport() {
        Airport airport = new Airport(1, "JFK", "New York", 6);

        when(airportRepository.save(airport))
                .thenReturn(airport);

        Airport result = airportService.createAirport(airport);

        assertNotNull(result);
        verify(airportRepository, times(1)).save(airport);
    }

    @Test
    @DisplayName("Update airport")
    void testUpdateAirport() {
        int id = 1;
        Airport existingAirport = new Airport(1, "JFK", "New York", 6);

        Airport updatedAirport = new Airport();
        updatedAirport.setName("LAX");
        updatedAirport.setCityName("Los Angeles");
        updatedAirport.setNrOfTerminals(4);

        when(airportRepository.findById(id))
                .thenReturn(Optional.of(existingAirport));

        when(airportRepository.save(existingAirport))
                .thenReturn(existingAirport);

        Airport result = airportService.updateAirport(id, updatedAirport);

        assertNotNull(result);
        assertEquals("LAX", result.getName());
        assertEquals("Los Angeles", result.getCityName());
        assertEquals(4, result.getNrOfTerminals());
        verify(airportRepository, times(1)).findById(id);
        verify(airportRepository, times(1)).save(existingAirport);
    }

    @Test
    @DisplayName("Delete airport")
    void testDeleteAirport() {
        int id = 1;

        doNothing().when(airportRepository).deleteById(id);

        airportService.deleteAirport(id);

        verify(airportRepository, times(1)).deleteById(id);
    }
}
