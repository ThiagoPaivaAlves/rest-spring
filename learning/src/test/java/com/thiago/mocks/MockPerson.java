package com.thiago.mocks;

import com.thiago.models.PersonDto;

public class MockPerson {

    public static PersonDto mockPersonDto() {
        return PersonDto.builder()
                        .key(999L)
                        .firstName("test")
                        .lastName("dto")
                        .address("address")
                        .gender("M")
                        .enabled(true).build();
    }

    public static com.thiago.models.entities.Person mockPersonEntity() {
        return new com.thiago.models.entities.Person(999L, "test", "entity", "address", "F", true);
    }
}
