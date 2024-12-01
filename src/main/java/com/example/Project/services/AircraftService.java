package com.example.Project.services;

import com.example.Project.model.entities.Aircraft;
import com.example.Project.model.entities.Airport;
import com.example.Project.repositories.AircraftRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class AircraftService {
    private final AircraftRepository aircraftRepository;

    public List<Aircraft> findAircraftByName(final String name) {
        return aircraftRepository.findAircraftsByName(name)
                .orElseThrow();
    }
    public Aircraft findAircraftById (final int id){
        return aircraftRepository.findById(id)
                .orElseThrow();
    }
    public List<Aircraft> getAllAircrafts() {
        return aircraftRepository.findAll();
    }
    public Aircraft createAircraft(final Aircraft aircraft) {
        return aircraftRepository.save(aircraft);
    }
    public Aircraft updateAircraft(final int id, final Aircraft aircraft) {
        Aircraft existingAircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aircraft not found"));


        existingAircraft.setName(aircraft.getName());
        existingAircraft.setNrOfSeatsEconomy(aircraft.getNrOfSeatsEconomy());
        existingAircraft.setNrOfSeatsFirstClass(aircraft.getNrOfSeatsFirstClass());
        existingAircraft.setMaxSpeed(aircraft.getMaxSpeed());
        existingAircraft.setWeight(aircraft.getWeight());
        existingAircraft.setWingSpan(aircraft.getWingSpan());

        return aircraftRepository.save(aircraft);
    }
    public void deleteAircraft(final int id) {
         aircraftRepository.deleteById(id);
    }
}
