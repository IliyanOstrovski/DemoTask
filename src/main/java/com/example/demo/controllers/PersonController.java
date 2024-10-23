package com.example.demo.controllers;

import com.example.demo.models.Person;
import com.example.demo.services.PersonService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/persons")
@Validated
public class PersonController {

    private final PersonService service;
    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    public PersonController(PersonService service) {
        this.service = service;
    }

    // 1. Регистриране на ФЛ
    @PostMapping
    public ResponseEntity<Person> registerPerson(@Valid @RequestBody Person person) {
        Person savedLice = service.savePerson(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLice);
    }



    // 2. Търсене на ФЛ по ЕГН
    @GetMapping("/egn/{egn}")
    public ResponseEntity<Person> getByEgn(@PathVariable String egn) {
        return service.getByEgn(egn)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // 3. Търсене на ФЛ по части от фамилия и възраст
    @GetMapping()
    public ResponseEntity<List<Person>> search(
            @RequestParam String firstName,
            @RequestParam(defaultValue = "0") int minAge,
            @RequestParam(required = false) Integer maxAge) {

        if (maxAge == null){
            throw new NullPointerException("Полето е задължително");
        }
        else if (minAge > maxAge){
            throw new IllegalArgumentException("Максималните години не могат да бъдат по-малко от минималните.");
        }

        List<Person> results = service.searchByFirstNameAndAge(firstName, minAge, maxAge);
        return ResponseEntity.ok(results);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updateById(
            @PathVariable Long id,
            @RequestBody Person updatedLice) {
        return service.updateById(id, updatedLice)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

}


