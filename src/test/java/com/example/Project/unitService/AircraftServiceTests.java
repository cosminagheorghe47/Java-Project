package com.example.Project.unitService;


import com.example.Project.model.entities.Aircraft;
import com.example.Project.repositories.AircraftRepository;
import com.example.Project.services.AircraftService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AircraftServiceTests {

    private final AircraftRepository aircraftRepository = mock(AircraftRepository.class);
    private final AircraftService aircraftService = new AircraftService(aircraftRepository);

    @Test
    @DisplayName("Find aircraft by name - valid name")
    void testFindAircraftByName_Valid() {
        String name = "Boeing 747";
        Aircraft aircraft = new Aircraft();
        aircraft.setName(name);

        when(aircraftRepository.findAircraftsByName(name))
                .thenReturn(Optional.of(Arrays.asList(aircraft)));

        List<Aircraft> result = aircraftService.findAircraftByName(name);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(name, result.get(0).getName());
        verify(aircraftRepository, times(1)).findAircraftsByName(name);
    }

    @Test
    @DisplayName("Find aircraft by name - invalid name")
    void testFindAircraftByName_Invalid() {
        String name = "Invalid Aircraft";

        when(aircraftRepository.findAircraftsByName(name))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> aircraftService.findAircraftByName(name));
        verify(aircraftRepository, times(1)).findAircraftsByName(name);
    }

    @Test
    @DisplayName("Find aircraft by ID - valid ID")
    void testFindAircraftById_Valid() {
        int id = 1;
        Aircraft aircraft = new Aircraft(id, "Boeing 747", 300, 50, 900, 180000, 60);
        //aircraft.setId(id);

        when(aircraftRepository.findById(id))
                .thenReturn(Optional.of(aircraft));

        Aircraft result = aircraftService.findAircraftById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(aircraftRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Find aircraft by ID - invalid ID")
    void testFindAircraftById_Invalid() {
        int id = 999;

        when(aircraftRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> aircraftService.findAircraftById(id));
        verify(aircraftRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Get all aircrafts")
    void testGetAllAircrafts() {
        Aircraft aircraft1 = new Aircraft(1, "Boeing 747", 300, 50, 900, 180000, 60);
        Aircraft aircraft2 = new Aircraft(2, "Boeing 748", 300, 50, 900, 180000, 60);

        when(aircraftRepository.findAll())
                .thenReturn(Arrays.asList(aircraft1, aircraft2));

        List<Aircraft> result = aircraftService.getAllAircrafts();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(aircraftRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Create new aircraft")
    void testCreateAircraft() {
        Aircraft aircraft = new Aircraft(1, "Boeing 747", 300, 50, 900, 180000, 60);

        when(aircraftRepository.save(aircraft))
                .thenReturn(aircraft);

        Aircraft result = aircraftService.createAircraft(aircraft);

        assertNotNull(result);
        verify(aircraftRepository, times(1)).save(aircraft);
    }

    @Test
    @DisplayName("Update aircraft")
    void testUpdateAircraft() {
        int id = 1;
        Aircraft existingAircraft = new Aircraft(id, "Boeing 747", 300, 50, 900, 180000, 60);
        existingAircraft.setId(id);
        existingAircraft.setName("Old Name");

        Aircraft updatedAircraft = new Aircraft(2, "Boeing 747", 300, 50, 900, 180000, 60);
        updatedAircraft.setName("New Name");

        when(aircraftRepository.findById(id))
                .thenReturn(Optional.of(existingAircraft));

        when(aircraftRepository.save(existingAircraft))
                .thenReturn(existingAircraft);

        Aircraft result = aircraftService.updateAircraft(id, updatedAircraft);

        assertNotNull(result);
        assertEquals("New Name", result.getName());
        verify(aircraftRepository, times(1)).findById(id);
        verify(aircraftRepository, times(1)).save(existingAircraft);
    }

    @Test
    @DisplayName("Delete aircraft")
    void testDeleteAircraft() {
        int id = 1;

        doNothing().when(aircraftRepository).deleteById(id);

        aircraftService.deleteAircraft(id);

        verify(aircraftRepository, times(1)).deleteById(id);
    }
}

