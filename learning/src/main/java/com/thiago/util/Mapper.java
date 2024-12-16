package com.thiago.util;

import com.thiago.models.PersonDto;
import com.thiago.models.entities.Person;

public class Mapper {

    public static PersonDto personMapper(Person person) {
        return PersonDto.builder().key(person.getId())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .address(person.getAddress())
                .gender(person.getGender())
                .build();

    }

    public static Person personDtoMapper(PersonDto personDto) {
        return new Person(personDto.getKey(), personDto.getFirstName(), personDto.getLastName(), personDto.getAddress(),
                personDto.getGender());
    }
}
