package com.thiago.controllers;

import com.thiago.models.PersonDto;
import com.thiago.services.PersonServices;
import com.thiago.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("person")
public class PersonController {

    @Autowired
    PersonServices service;

    @GetMapping("/{id}")
    public PersonDto findPerson(@PathVariable("id") Long id){
        return service.findById(id);
    }

    @GetMapping()
    public List<PersonDto> findAllPerson(){
        return service.findAll();
    }

    @PostMapping()
    public PersonDto createPerson(@RequestBody PersonDto personDto) {
        return service.create(personDto);
    }

    @PutMapping("/{id}")
    public PersonDto updatePerson(@PathVariable Long id, @RequestBody PersonDto personDto) {
        return Mapper.personMapper(service.update(id, personDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePerson(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
