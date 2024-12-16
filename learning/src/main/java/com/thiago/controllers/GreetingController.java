package com.thiago.controllers;

import com.thiago.models.Greeting;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("learning")
public class GreetingController {

    private final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/hello")
    public Greeting helloWorld(@RequestParam(value = "name", defaultValue="test") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @GetMapping("/sum/{numOne}/{numTwo}")
    public double sum(@PathVariable("numOne") Double numOne, @PathVariable("numTwo") Double numTwo) {
        return numOne + numTwo;
    }

    @GetMapping("/sub/{numOne}/{numTwo}")
    public double sub(@PathVariable("numOne") Double numOne, @PathVariable("numTwo") Double numTwo) {
        return numOne - numTwo;
    }

    @GetMapping("/div/{numOne}/{numTwo}")
    public double div(@PathVariable("numOne") Double numOne, @PathVariable("numTwo") Double numTwo) {
        return numOne / numTwo;
    }

    @GetMapping("/mul/{numOne}/{numTwo}")
    public double mul(@PathVariable("numOne") Double numOne, @PathVariable("numTwo") Double numTwo) {
        return numOne * numTwo;
    }

    @GetMapping("/avg/{numOne}/{numTwo}")
    public double avg(@PathVariable("numOne") Double numOne, @PathVariable("numTwo") Double numTwo) {
        return (numOne + numTwo)/2;
    }

    @GetMapping("/pow/{numOne}/{numTwo}")
    public double pow(@PathVariable("numOne") Double numOne, @PathVariable("numTwo") Double numTwo) {
        return Math.pow(numOne, numTwo);
    }

    @GetMapping("/sqrt/{num}")
    public double sqrt(@PathVariable("num") Double num) {
        return Math.sqrt(num);
    }
}
