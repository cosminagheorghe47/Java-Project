package com.example.Project.controllers;

import com.example.Project.model.entities.Person;
import com.example.Project.services.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/person")
public class PersonController {
    private final PersonService personService;

    @GetMapping("/nationality/{nationality}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Person> getPersonsByNationality(@PathVariable final String nationality) {
        return personService.findPersonsByNationality(nationality);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Person> getPerson(@PathVariable final int id) {
        Person person = personService.findPersonById(id);
        if (person != null) {
            return ResponseEntity.ok(person);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('CLIENT')")
    public Person createPerson(@RequestBody final Person person) {
        return personService.createPerson(person);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Person> updatePerson(@PathVariable final int id, @RequestBody final Person person) {
        Person updatedPerson = personService.updatePerson(id, person);
        if (updatedPerson != null) {
            return ResponseEntity.ok(updatedPerson);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<Void> deletePerson(@PathVariable final int id) {
        if (personService.deletePerson(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
