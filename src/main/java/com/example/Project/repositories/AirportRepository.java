package com.example.Project.repositories;

import com.example.Project.model.entities.Aircraft;
import com.example.Project.model.entities.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AirportRepository extends JpaRepository<Airport, Integer> {
    @Query(value = """
        SELECT i FROM Airport i WHERE i.cityName = :name
    """)
    Optional<List<Airport>> findAirportsByCity(String name);
}
