package com.thiago.services;

import com.thiago.controllers.PersonController;
import com.thiago.exceptions.NotFoundException;
import com.thiago.models.PersonDto;
import com.thiago.models.entities.Person;
import com.thiago.repositories.PersonRepository;
import com.thiago.util.Mapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PersonService {

    @Autowired
    PersonRepository repository;

    public PersonDto findById(Long id) {
        log.info("returning person");
        PersonDto dto = Mapper.personMapper(repository.findById(id).orElseThrow(
                () -> new NotFoundException("tem nada nao")));
        dto.add(linkTo(methodOn(PersonController.class).findPerson(id)).withSelfRel());
        return dto;
    }

    public List<PersonDto> findAll() {
        log.info("returning all");
        return repository.findAll().stream().map(entity -> {
            PersonDto dto = Mapper.personMapper(entity);
            dto.add(linkTo(methodOn(PersonController.class).findAllPerson()).withSelfRel());
            return dto;
        }).collect(Collectors.toList());
    }

    @SneakyThrows
    public PersonDto create(PersonDto personDto) {
        log.info("creating person");

        if(personDto == null) {
            throw new BadRequestException("You need to provide person data for person creation");
        }

        PersonDto dto = Mapper.personMapper(repository.save(Mapper.personDtoMapper(personDto)));
        dto.add(linkTo(methodOn(PersonController.class).findPerson(dto.getKey())).withSelfRel());
        return dto;
    }

    @SneakyThrows
    public PersonDto update(Long id, PersonDto personDto) {
        log.info("updating person");

        repository.findById(id).orElseThrow( () -> new NotFoundException("tem nada nao"));

        if(personDto == null) {
            throw new BadRequestException("You need to provide Person for updating");
        }

        Person entity = Mapper.personDtoMapper(personDto);
        entity.setId(id);
        PersonDto dto = Mapper.personMapper(repository.save(entity));
        dto.add(linkTo(methodOn(PersonController.class).findPerson(dto.getKey())).withSelfRel());
        return dto;
    }

    public void delete(Long id) {
        log.info("deleting person");

        repository.findById(id).orElseThrow( () -> new NotFoundException("tem nada nao"));
        repository.deleteById(id);

        log.info("Person {} deleted", id);
    }

}