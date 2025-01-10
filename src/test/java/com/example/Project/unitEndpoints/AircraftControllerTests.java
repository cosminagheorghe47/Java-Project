package com.example.Project.unitEndpoints;

import com.example.Project.controllers.AircraftController;
import com.example.Project.model.entities.Aircraft;
import com.example.Project.services.AircraftService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AircraftControllerTests {

    private final AircraftService aircraftService = mock(AircraftService.class);
    private final AircraftController aircraftController = new AircraftController(aircraftService);

    @Test
    @DisplayName("Get aircrafts by name")
    void testGetAircraftsByName() {
        String name = "Boeing 747";
        Aircraft aircraft = new Aircraft(1, name, 300, 50, 900, 180000, 60);

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
        Aircraft aircraft = new Aircraft(id, "Boeing 747", 300, 50, 900, 180000, 60);

        when(aircraftService.findAircraftById(id))
                .thenReturn(aircraft);

        Aircraft result = aircraftController.getAircraft(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Boeing 747", result.getName());
        verify(aircraftService, times(1)).findAircraftById(id);
    }

    @Test
    @DisplayName("Get all aircrafts")
    void testGetAllAircrafts() {
        Aircraft aircraft1 = new Aircraft(1, "Boeing 747", 300, 50, 900, 180000, 60);
        Aircraft aircraft2 = new Aircraft(2, "Boeing 748", 320, 55, 920, 190000, 62);

        when(aircraftService.getAllAircrafts())
                .thenReturn(Arrays.asList(aircraft1, aircraft2));

        List<Aircraft> result = aircraftController.getAllAircrafts();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Boeing 747", result.get(0).getName());
        assertEquals("Boeing 748", result.get(1).getName());
        verify(aircraftService, times(1)).getAllAircrafts();
    }

    @Test
    @DisplayName("Create aircraft")
    void testCreateAircraft() {
        Aircraft aircraft = new Aircraft(1, "Boeing 747", 300, 50, 900, 180000, 60);

        when(aircraftService.createAircraft(aircraft))
                .thenReturn(aircraft);

        Aircraft result = aircraftController.createAircraft(aircraft);

        assertNotNull(result);
        assertEquals("Boeing 747", result.getName());
        verify(aircraftService, times(1)).createAircraft(aircraft);
    }

    @Test
    @DisplayName("Update aircraft")
    void testUpdateAircraft() {
        int id = 1;
        Aircraft existingAircraft = new Aircraft(id, "Boeing 747", 300, 50, 900, 180000, 60);
        Aircraft updatedAircraft = new Aircraft(id, "Boeing 748", 320, 55, 920, 190000, 62);

        when(aircraftService.updateAircraft(id, updatedAircraft))
                .thenReturn(updatedAircraft);

        Aircraft result = aircraftController.updateAircraft(id, updatedAircraft);

        assertNotNull(result);
        assertEquals("Boeing 748", result.getName());
        verify(aircraftService, times(1)).updateAircraft(id, updatedAircraft);
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
