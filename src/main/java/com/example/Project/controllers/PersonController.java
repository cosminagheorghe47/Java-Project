package com.example.Project.controllers;

import com.example.Project.model.entities.Person;
import com.example.Project.services.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/person")
public class PersonController {
    private final PersonService personService;

    @GetMapping("/nationality/{nationality}")
    public List<Person> getPersonsByNationality(@PathVariable final String nationality) {
        return personService.findPersonsByNationality(nationality);
    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable final int id) {
        return personService.findPersonById(id);
    }

    @GetMapping("/")
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    @PostMapping("/")
    public Person createPerson(@RequestBody final Person person) {
        System.out.println(person.toString()+"AICIIIIIIIII");
        return personService.createPerson(person);
    }

    @PutMapping("/{id}")
    public Person updatePerson(@PathVariable final int id, @RequestBody final Person person) {
        return personService.updatePerson(id, person);
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable final int id) {
        personService.deletePerson(id);
    }
}
