package com.example.demo.controllers;

import com.example.demo.dto.PersonSearchDTO;
import com.example.demo.models.Person;
import com.example.demo.services.PersonService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/persons")
@Validated
public class PersonController {

    private final PersonService service;

    // Валидация на празно поле
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

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

    // 3. Търсене на ФЛ по име и възраст
    @GetMapping("/search")
    public ResponseEntity<List<Person>> search(@Valid PersonSearchDTO request) {

        List<Person> results = service.searchByFirstNameAndAge(
                request.getFirstName(),
                request.getMinAge(),
                request.getMaxAge()
        );
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        boolean isDeleted = service.deleteById(id);
        return isDeleted ? ResponseEntity.noContent().build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}


