package com.thiago.controllers;

import com.thiago.models.Greeting;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/hello")
    public Greeting helloWorld(@RequestParam(value = "name", defaultValue="test") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}
