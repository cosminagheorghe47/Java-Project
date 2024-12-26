package com.example.Project.repositories;

import com.example.Project.model.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;


import com.example.Project.model.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Integer> {

    @Query("SELECT f FROM Flight f " +
            "JOIN f.departureAirport da " +
            "JOIN f.arrivalAirport aa " +
            "WHERE (:arrivalDate IS NULL OR DATE(f.arrivalDate) = DATE(:arrivalDate)) " +
            "AND (:departureDate IS NULL OR DATE(f.departureDate) = DATE(:departureDate)) " +
            "AND (:arrivalAirport IS NULL OR aa.id = :arrivalAirport) " +
            "AND (:departureAirport IS NULL OR da.id = :departureAirport)")
    List<Flight> searchFlights(
            @Param("arrivalDate") Date arrivalDate,
            @Param("departureDate") Date departureDate,
            @Param("arrivalAirport") Integer arrivalAirport,
            @Param("departureAirport") Integer departureAirport
    );
}

