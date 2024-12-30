package com.thiago.services;

import com.thiago.controllers.PersonController;
import com.thiago.controllers.UserController;
import com.thiago.exceptions.NotFoundException;
import com.thiago.models.UserDto;
import com.thiago.security.components.Encrypter;
import com.thiago.security.models.entities.User;
import com.thiago.security.repositories.UserRepository;
import com.thiago.util.Mapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@Slf4j
public class SystemUserService {
 
    @Autowired
    private UserRepository repository;
    
    @Autowired
    private Encrypter encrypter;
    
    public UserDto findById(Long id) {
        
        log.info("Finding a user");
        
        UserDto response = Mapper.userMapper(repository.findById(id).orElseThrow(NotFoundException::new));
        response.add(linkTo(methodOn(UserController.class).findUser(id)).withSelfRel());
        
        return response;
    }
    
    public List<UserDto> findAll() {
        log.info("returning all");
        return repository.findAll().stream().map(entity -> {
            UserDto dto = Mapper.userMapper(entity);
            dto.add(linkTo(methodOn(UserController.class).findAllUsers()).withSelfRel());
            return dto;
        }).collect(Collectors.toList());
    }
    
    @SneakyThrows
    public UserDto create(UserDto userDto) {
        log.info("creating user");
        
        if(userDto == null) {
            throw new BadRequestException("You need to provide user data for its creation");
        }
        
        userDto.setPassword(encrypter.encrypt(userDto.getPassword()));
        
        UserDto dto = Mapper.userMapper(repository.save(Mapper.userDtoMapper(userDto)));
        dto.add(linkTo(methodOn(UserController.class).findUser(dto.getKey())).withSelfRel());
        return dto;
    }
    
    @SneakyThrows
    public UserDto update(Long id, UserDto userDto) {
        log.info("updating user");
        
        User currentUser = repository.findById(id).orElseThrow(() -> new NotFoundException("tem nada nao"));
        
        if (userDto == null) {
            throw new BadRequestException("You need to provide User data for updating");
        }
        
        if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            userDto.setPassword(currentUser.getPassword());
        } else {
            userDto.setPassword(encrypter.encrypt(userDto.getPassword()));
        }
        
        User entity = Mapper.userDtoMapper(userDto);
        entity.setId(id);
        UserDto dto = Mapper.userMapper(repository.save(entity));
        dto.add(linkTo(methodOn(PersonController.class).findPerson(dto.getKey())).withSelfRel());
        return dto;
    }
    
    public void delete(Long id) {
        log.info("deleting user");
        
        repository.findById(id).orElseThrow( () -> new NotFoundException("tem nada nao"));
        repository.deleteById(id);
        
        log.info("User {} deleted", id);
    }
}