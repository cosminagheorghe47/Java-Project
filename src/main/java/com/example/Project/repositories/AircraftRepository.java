package com.example.Project.repositories;

import com.example.Project.model.entities.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AircraftRepository extends JpaRepository<Aircraft, Integer> {
    @Query(value = """
        SELECT i FROM Aircraft i WHERE i.name = :name
    """)
    Optional<List<Aircraft>>  findAircraftsByName(String name);
}
