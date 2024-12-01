package com.example.Project.repositories;

import com.example.Project.model.entities.Airport;
import com.example.Project.model.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    @Query(value = """
        SELECT i FROM Person i WHERE i.nationality = :name
    """)
    Optional<List<Person>> findPersonsByNationality(String nationality);
}
