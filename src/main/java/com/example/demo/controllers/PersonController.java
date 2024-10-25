package com.example.demo.controllers;

import com.example.demo.dto.PersonRegisterDTO;
import com.example.demo.dto.PersonUpdateDTO;
import com.example.demo.models.Person;
import com.example.demo.services.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
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

    public PersonController(PersonService service) {
        this.service = service;
    }

    // 1. Регистриране на ФЛ
    @PostMapping
    @Tag(name = "Регистрация", description = "Person API")
    @Operation(summary = "Регистрация на физическо лице", description = "Попълнете полето долу", responses = {@ApiResponse(responseCode = "201", description = "Физическото лице е регистрирано успешно!"), @ApiResponse(responseCode = "200", description = "Успешна регистрация"), @ApiResponse(responseCode = "400", description = "Невалидни данни"), @ApiResponse(responseCode = "500", description = "Сървърна грешка")})
    public ResponseEntity<Person> registerPerson(@Valid @RequestBody PersonRegisterDTO personDTO) {
        Person person = new Person();
        person.setFirstName(personDTO.getFirstName());
        person.setMiddleName(personDTO.getMiddleName());
        person.setLastName(personDTO.getLastName());
        person.setEgn(personDTO.getEgn());
        person.setAge(personDTO.getAge());

        Person savedPerson = service.savePerson(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPerson);
    }

    // 2. Търсене на ФЛ по ЕГН
    @GetMapping("/egn/{egn}")
    @Tag(name = "Търсене по ЕГН", description = "Person API")
    @Operation(summary = "Търсене на физическо лице по ЕГН", description = "Попълнете полето долу", responses = {@ApiResponse(responseCode = "200", description = "Физическото лице е намерено успешно!"), @ApiResponse(responseCode = "400", description = "Невалидни данни"), @ApiResponse(responseCode = "500", description = "Сървърна грешка")})
    public ResponseEntity<Person> getByEgn(@PathVariable String egn) {
        return service.getByEgn(egn).map(ResponseEntity::ok).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // 3. Търсене на ФЛ по име и възраст
    @GetMapping("/search")
    @Tag(name = "Търсене по Име и години", description = "Person API")
    @Operation(summary = "Търсене на физическо лице по име и години", description = "Попълнете полето долу", responses = {@ApiResponse(responseCode = "200", description = "Физическото лице е намерено успешно!"), @ApiResponse(responseCode = "400", description = "Невалидни данни"), @ApiResponse(responseCode = "500", description = "Сървърна грешка")})
    public ResponseEntity<List<Person>> search(@RequestParam(required = false) String firstName, @RequestParam(required = false) @Min(0) @Max(120) Integer minAge, @RequestParam(required = false) @Min(0) @Max(120) Integer maxAge) {

        // Manual validation for age range
        if (minAge != null && maxAge != null && minAge > maxAge) {
            return ResponseEntity.badRequest().body(null);
        }

        List<Person> results = service.findByFirstNameAndAgeBetween(firstName, minAge, maxAge);

        return ResponseEntity.ok(results);
    }

    // 4. Обновяване на ФЛ по идентификатор
    @PutMapping("/{id}")
    @Tag(name = "Обновяване на данни", description = "Person API")
    @Operation(summary = "Обновяване на физическо лице по идентификатор", description = "Попълнете полето долу", responses = {@ApiResponse(responseCode = "201", description = "Физическото лице е с обновени данни успешно!"), @ApiResponse(responseCode = "400", description = "Невалидни данни"), @ApiResponse(responseCode = "500", description = "Сървърна грешка")})
    public ResponseEntity<Person> updateById(@PathVariable Long id, @Valid @RequestBody PersonUpdateDTO updatedDTO) {
        Person updatedPerson = new Person();
        updatedPerson.setFirstName(updatedDTO.getFirstName());
        updatedPerson.setMiddleName(updatedDTO.getMiddleName());
        updatedPerson.setLastName(updatedDTO.getLastName());
        updatedPerson.setEgn(updatedDTO.getEgn());
        updatedPerson.setAge(updatedDTO.getAge());

        return service.updateById(id, updatedPerson).map(ResponseEntity::ok).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // 5. Изтриване на ФЛ по идентификатор
    @DeleteMapping("/{id}")
    @Tag(name = "Изтриване на ФЛ по идентификатор", description = "Person API")
    @Operation(summary = "Изтриване на физическо лице по идентификатор", description = "Попълнете полето долу", responses = {@ApiResponse(responseCode = "201", description = "Физическото лице е изтрито успешно!"), @ApiResponse(responseCode = "400", description = "Невалидни данни"), @ApiResponse(responseCode = "500", description = "Сървърна грешка")})
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        boolean isDeleted = service.deleteById(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}