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
import jakarta.validation.constraints.Size;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/persons")
@Validated
public class PersonController {

    private final PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

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

    @GetMapping("/search")
    @Tag(name = "Търсене на съществуващо ФЛ", description = "Person API")
    @Operation(summary = "Търсене на същестуващо физическо лице", description = "Попълнете полето долу", responses = {@ApiResponse(responseCode = "200", description = "Физическото лице е намерено успешно!"), @ApiResponse(responseCode = "400", description = "Невалидни данни"), @ApiResponse(responseCode = "500", description = "Сървърна грешка")})
    public ResponseEntity<List<Person>> search(@RequestParam(required = false) String firstName,
                                               @RequestParam(required = false) @Min(1) @Max(120) Integer minAge,
                                               @RequestParam(required = false) @Min(10) @Max(10) String egn) {
        // Validate parameters
        if (firstName == null && minAge == null && egn == null) {
            return ResponseEntity.badRequest().body(null); // Or return a custom error message
        }

        List<Person> results = service.findAllFirstNameAndAgeAndEgn(firstName, minAge, egn);
        return ResponseEntity.ok(results);
    }

    @Tag(name = "Търсене всички съществуващи ФЛ по име ", description = "Person API")
    @Operation(summary = "Търсене на същестуващо физическо лице",
            description = "Попълнете полето долу",
            responses = {@ApiResponse(responseCode = "200", description = "Физическото лице е намерено успешно!"),
                    @ApiResponse(responseCode = "400", description = "Не може да бъде отрицателно число!"),
                    @ApiResponse(responseCode = "500", description = "Сървърна грешка")})
    @RequestMapping(value = "/quickSearchAction", method = RequestMethod.GET)
    public @ResponseBody List<Person> quickSearchAction(
            @RequestParam(value = "firstName") String firstName, Pageable pageable
    ) {
        return service.findPersonByFirstNameStartWith(firstName, pageable);
    }
   /* @RequestMapping(value = "/quickSearchAction", method = RequestMethod.GET)
    public @ResponseBody List<Person> quickSearchAction(
            @RequestParam(value = "firstName") String firstName,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "1") int size) {

*//*        if (page < 0) {
            return ResponseEntity.badRequest().body("Page number must not be negative");
        }
        if (size < 1) {
            return ResponseEntity.badRequest().body("Size must be at least 1");
        }*//*
        // Create a Pageable instance without sorting
        Pageable pageable = PageRequest.of(page, size);

        // Perform the search
        return service.personPagingRepository("%" + firstName + "%", pageable);
    }*/

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

    @DeleteMapping("/{id}")
    @Tag(name = "Изтриване на ФЛ по идентификатор", description = "Person API")
    @Operation(summary = "Изтриване на физическо лице по идентификатор", description = "Попълнете полето долу", responses = {@ApiResponse(responseCode = "201", description = "Физическото лице е изтрито успешно!"), @ApiResponse(responseCode = "400", description = "Невалидни данни"), @ApiResponse(responseCode = "500", description = "Сървърна грешка")})
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        boolean isDeleted = service.deleteById(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}