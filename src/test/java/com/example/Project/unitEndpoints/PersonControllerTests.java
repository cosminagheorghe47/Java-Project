package com.example.Project.unitEndpoints;

import com.example.Project.controllers.PersonController;
import com.example.Project.model.entities.Person;
import com.example.Project.services.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PersonControllerTests {

    private PersonService personService;
    private PersonController personController;

    @BeforeEach
    void setUp() {
        personService = mock(PersonService.class);
        personController = new PersonController(personService);
    }

    @Test
    void testGetPerson() {
        Person person = new Person();
        person.setId(1);
        person.setFirstName("Maria");

        when(personService.findPersonById(1)).thenReturn(person);

        ResponseEntity<Person> response = personController.getPerson(1);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Maria", response.getBody().getFirstName());
        verify(personService, times(1)).findPersonById(1);
    }

    @Test
    void testGetPerson_NotFound() {
        when(personService.findPersonById(1)).thenReturn(null);

        ResponseEntity<Person> response = personController.getPerson(1);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(personService, times(1)).findPersonById(1);
    }

    @Test
    void testUpdatePerson() {
        Person existingPerson = new Person();
        existingPerson.setId(1);
        existingPerson.setFirstName("Maria");

        Person updatedPerson = new Person();
        updatedPerson.setFirstName("Ana");

        when(personService.updatePerson(1, updatedPerson)).thenReturn(updatedPerson);

        ResponseEntity<Person> response = personController.updatePerson(1, updatedPerson);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Ana", response.getBody().getFirstName());
        verify(personService, times(1)).updatePerson(1, updatedPerson);
    }



    @Test
    void testDeletePerson() {
        when(personService.deletePerson(1)).thenReturn(true);

        ResponseEntity<Void> response = personController.deletePerson(1);

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(personService, times(1)).deletePerson(1);
    }

    @Test
    void testDeletePerson_NotFound() {
        when(personService.deletePerson(1)).thenReturn(false);

        ResponseEntity<Void> response = personController.deletePerson(1);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        verify(personService, times(1)).deletePerson(1);
    }
}
