package com.example.Project.services;

import com.example.Project.model.entities.Person;
import com.example.Project.repositories.PersonRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    public List<Person> findPersonsByNationality(final String nationality) {
        return personRepository.findPersonsByNationality(nationality)
                .orElseThrow(() -> new RuntimeException("Person not found"));
    }

    public Person findPersonById(final int id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found"));
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Person createPerson(final Person person) {
        System.out.println(person.toString()+"AICIIIIIIIII");
        return personRepository.save(person);
    }

    public Person updatePerson(final int id, final Person person) {
        Person existingPerson = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found"));

        existingPerson.setFirstName(person.getFirstName());
        existingPerson.setLastName(person.getLastName());
        existingPerson.setCnp(person.getCnp());
        existingPerson.setGender(person.getGender());
        existingPerson.setAge(person.getAge());
        existingPerson.setNationality(person.getNationality());

        return personRepository.save(existingPerson);
    }

    public boolean deletePerson(final int id) {
        try {
            personRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
