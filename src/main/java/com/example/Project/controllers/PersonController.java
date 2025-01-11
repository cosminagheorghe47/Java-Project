package com.example.Project.controllers;

import com.example.Project.model.entities.Person;
import com.example.Project.services.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/person")
@Tag(name = "Person", description = "Operations for managing persons")
public class PersonController {
    private final PersonService personService;

    @Operation(summary = "Get persons by nationality",
            description = "Fetches persons based on their nationality. Only accessible by admins.")
    @GetMapping("/nationality/{nationality}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Person> getPersonsByNationality(@PathVariable String nationality) {
        return personService.findPersonsByNationality(nationality);
    }

    @Operation(summary = "Get person by ID",
            description = "Fetches a person by their ID. Returns 404 if the person is not found. Accessible by admins.")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Person> getPerson(@PathVariable int id) {
        Person person = personService.findPersonById(id);
        if (person != null) {
            return ResponseEntity.ok(person);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get all persons",
            description = "Fetches a list of all persons in the system. Accessible by admins.")
    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    @Operation(summary = "Create a new person",
            description = "Allows a client to create a new person record. Only accessible by clients.")
    @PostMapping("/")
    @PreAuthorize("hasRole('CLIENT')")
    public Person createPerson(@RequestBody Person person) {
        return personService.createPerson(person);
    }

    @Operation(summary = "Update person details",
            description = "Allows a client to update a person's details based on their ID. Returns 404 if not found.")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Person> updatePerson(@PathVariable int id, @RequestBody Person person) {
        Person updatedPerson = personService.updatePerson(id, person);
        if (updatedPerson != null) {
            return ResponseEntity.ok(updatedPerson);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a person",
            description = "Allows a client to delete a person by their ID. Returns 404 if the person is not found.")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Void> deletePerson(@PathVariable int id) {
        if (personService.deletePerson(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
