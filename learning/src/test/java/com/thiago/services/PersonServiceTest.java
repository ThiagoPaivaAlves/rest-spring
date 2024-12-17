package com.thiago.services;

import com.thiago.exceptions.NotFoundException;
import com.thiago.mocks.MockPerson;
import com.thiago.models.PersonDto;
import com.thiago.models.entities.Person;
import com.thiago.repositories.PersonRepository;
import com.thiago.util.Mapper;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @InjectMocks
    private PersonService service;

    @Mock
    private PersonRepository repository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Check if HATEOS link is empty")
    void findBydIdHateos() {

        Person entity = MockPerson.mockPersonEntity();

        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));

        PersonDto result = service.findById(1L);

        assertFalse(result.getLinks().stream().toList().isEmpty());
    }

    @Test
    @DisplayName("Check if result is compatible")
    void findBydIResult() {

        PersonDto dto = MockPerson.mockPersonDto().withLastName("entity").withGender("F");
        Person entity = MockPerson.mockPersonEntity();

        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));

        PersonDto result = service.findById(1L);
        assertTrue(new ReflectionEquals(dto, "links").matches(result));
    }

    @Test
    @DisplayName("Testing create person")
    void testCreatePerson() {

        Person entity = MockPerson.mockPersonEntity();

        when(repository.save(any(Person.class))).thenReturn(entity);

        PersonDto dto = MockPerson.mockPersonDto().withLastName("entity").withGender("F").withKey(null);
        PersonDto result = service.create(dto);

        assertTrue(new ReflectionEquals(entity, "links").matches(Mapper.personDtoMapper(result)));
    }

    @Test
    @DisplayName("Test throws exception when creating")
    void testCreatePersonThrowsException() {
        Exception exception = assertThrows(BadRequestException.class, () -> service.create(null));

        String expectedMessage = "You need to provide person data for person creation";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Test throws exception when updating")
    void testUpdatePersonThrowsException() {

        when(repository.findById(anyLong())).thenReturn(Optional.of(MockPerson.mockPersonEntity()));

        Exception exception = assertThrows(BadRequestException.class, () -> service.update(anyLong(), null));

        String expectedMessage = "You need to provide Person for updating";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Test throws not found exception when updating")
    void testUpdatePersonThrowsNotFoundException() {

        Exception exception = assertThrows(NotFoundException.class, () -> service.update(anyLong(), null));

        String expectedMessage = "tem nada nao";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Test throws not found exception when deleting")
    void testDeletePersonThrowsNotFoundException() {

        Exception exception = assertThrows(NotFoundException.class, () -> service.delete(anyLong()));

        String expectedMessage = "tem nada nao";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testUpdatePerson() {

        PersonDto dto = MockPerson.mockPersonDto().withFirstName("changed").withGender("F");
        Person wanted = MockPerson.mockPersonEntity().withFirstName("changed");

        when(repository.findById(anyLong())).thenReturn(Optional.of(MockPerson.mockPersonEntity()));
        when(repository.save(any(Person.class))).thenReturn(wanted);

        PersonDto result = service.update(1L, dto);

        assertTrue(new ReflectionEquals(wanted, "links").matches(Mapper.personDtoMapper(result)));
    }
}
