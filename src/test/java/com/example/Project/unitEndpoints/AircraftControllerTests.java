package com.example.Project.unitEndpoints;


import com.example.Project.controllers.AircraftController;
import com.example.Project.model.entities.Aircraft;
import com.example.Project.services.AircraftService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.OK;

public class AircraftControllerTests {

    private final AircraftService aircraftService = mock(AircraftService.class);
    private final AircraftController aircraftController = new AircraftController(aircraftService);

    @Test
    @DisplayName("Get aircrafts by name")
    void testGetAircraftsByName() {
        String name = "Boeing 747";
        Aircraft aircraft = new Aircraft();
        aircraft.setName(name);

        when(aircraftService.findAircraftByName(name))
                .thenReturn(Arrays.asList(aircraft));

        List<Aircraft> result = aircraftController.getAircrafts(name);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(name, result.get(0).getName());
        verify(aircraftService, times(1)).findAircraftByName(name);
    }

    @Test
    @DisplayName("Get aircraft by ID")
    void testGetAircraftById() {
        int id = 1;
        Aircraft aircraft = new Aircraft();
        aircraft.setId(id);

        when(aircraftService.findAircraftById(id))
                .thenReturn(aircraft);

        Aircraft result = aircraftController.getAircraft(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(aircraftService, times(1)).findAircraftById(id);
    }

    @Test
    @DisplayName("Get all aircrafts")
    void testGetAllAircrafts() {
        Aircraft aircraft1 = new Aircraft();
        Aircraft aircraft2 = new Aircraft();

        when(aircraftService.getAllAircrafts())
                .thenReturn(Arrays.asList(aircraft1, aircraft2));

        List<Aircraft> result = aircraftController.getAllAircrafts();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(aircraftService, times(1)).getAllAircrafts();
    }

    @Test
    @DisplayName("Create aircraft")
    void testCreateAircraft() {
        Aircraft aircraft = new Aircraft();

        when(aircraftService.createAircraft(aircraft))
                .thenReturn(aircraft);

        Aircraft result = aircraftController.createAircraft(aircraft);

        assertNotNull(result);
        verify(aircraftService, times(1)).createAircraft(aircraft);
    }

    @Test
    @DisplayName("Update aircraft")
    void testUpdateAircraft() {
        int id = 1;
        Aircraft aircraft = new Aircraft();

        when(aircraftService.updateAircraft(id, aircraft))
                .thenReturn(aircraft);

        Aircraft result = aircraftController.updateAircraft(id, aircraft);

        assertNotNull(result);
        verify(aircraftService, times(1)).updateAircraft(id, aircraft);
    }

    @Test
    @DisplayName("Delete aircraft")
    void testDeleteAircraft() {
        int id = 1;

        doNothing().when(aircraftService).deleteAircraft(id);

        aircraftController.deleteAircraft(id);

        verify(aircraftService, times(1)).deleteAircraft(id);
    }
}

