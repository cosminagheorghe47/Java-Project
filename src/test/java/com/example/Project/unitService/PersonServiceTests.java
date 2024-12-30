package com.example.Project.unitService;

import com.example.Project.model.entities.Person;
import com.example.Project.repositories.PersonRepository;
import com.example.Project.services.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PersonServiceTests {

    private PersonRepository personRepository;
    private PersonService personService;

    @BeforeEach
    void setUp() {
        personRepository = mock(PersonRepository.class);
        personService = new PersonService(personRepository);
    }

    @Test
    void testFindPersonsByNationality() {
        Person person1 = new Person();
        person1.setId(1);
        person1.setNationality("Romanian");

        when(personRepository.findPersonsByNationality("Romanian"))
                .thenReturn(Optional.of(Arrays.asList(person1)));

        List<Person> result = personService.findPersonsByNationality("Romanian");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Romanian", result.get(0).getNationality());
        verify(personRepository, times(1)).findPersonsByNationality("Romanian");
    }

    @Test
    void testFindPersonsByNationality_NotFound() {
        when(personRepository.findPersonsByNationality("Unknown"))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> personService.findPersonsByNationality("Unknown"));
        verify(personRepository, times(1)).findPersonsByNationality("Unknown");
    }

    @Test
    void testFindPersonById() {
        Person person = new Person();
        person.setId(1);
        person.setFirstName("Maria");

        when(personRepository.findById(1)).thenReturn(Optional.of(person));

        Person result = personService.findPersonById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Maria", result.getFirstName());
        verify(personRepository, times(1)).findById(1);
    }

    @Test
    void testFindPersonById_NotFound() {
        when(personRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> personService.findPersonById(1));
        verify(personRepository, times(1)).findById(1);
    }

    @Test
    void testGetAllPersons() {
        Person person1 = new Person();
        person1.setId(1);

        Person person2 = new Person();
        person2.setId(2);

        when(personRepository.findAll()).thenReturn(Arrays.asList(person1, person2));

        List<Person> result = personService.getAllPersons();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(personRepository, times(1)).findAll();
    }

    @Test
    void testCreatePerson() {
        Person person = new Person();
        person.setId(1);
        person.setFirstName("Ana");

        when(personRepository.save(person)).thenReturn(person);

        Person result = personService.createPerson(person);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Ana", result.getFirstName());
        verify(personRepository, times(1)).save(person);
    }

    @Test
    void testUpdatePerson() {
        Person existingPerson = new Person();
        existingPerson.setId(1);
        existingPerson.setFirstName("Maria");

        Person updatedPerson = new Person();
        updatedPerson.setFirstName("Ana");

        when(personRepository.findById(1)).thenReturn(Optional.of(existingPerson));
        when(personRepository.save(existingPerson)).thenReturn(existingPerson);

        Person result = personService.updatePerson(1, updatedPerson);

        assertNotNull(result);
        assertEquals("Ana", result.getFirstName());
        verify(personRepository, times(1)).findById(1);
        verify(personRepository, times(1)).save(existingPerson);
    }

    @Test
    void testUpdatePerson_NotFound() {
        when(personRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> personService.updatePerson(1, new Person()));
        verify(personRepository, times(1)).findById(1);
    }

    @Test
    void testDeletePerson() {
        doNothing().when(personRepository).deleteById(1);

        personService.deletePerson(1);

        verify(personRepository, times(1)).deleteById(1);
    }
}
