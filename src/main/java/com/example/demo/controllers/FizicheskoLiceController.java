package com.example.demo.controllers;


import com.example.demo.models.FizicheskoLice;
import com.example.demo.services.FizicheskoLiceService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/fizicheski-lica")
@Validated
public class FizicheskoLiceController {

    private FizicheskoLiceService service;

    public FizicheskoLiceController(FizicheskoLiceService service) {
        this.service = service;
    }

    // 1. Регистриране на ФЛ
    @PostMapping
    public ResponseEntity<FizicheskoLice> registerFizicheskoLice(@Valid @RequestBody FizicheskoLice fizicheskoLice) {
        FizicheskoLice savedLice = service.saveFizicheskoLice(fizicheskoLice);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLice);
    }



    // 2. Търсене на ФЛ по ЕГН
    @GetMapping("/egn/{egn}")
    public ResponseEntity<FizicheskoLice> getByEgn(@PathVariable String egn) {
        return service.getByEgn(egn)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // 3. Търсене на ФЛ по части от фамилия и възраст
    @GetMapping("/search")
    public ResponseEntity<List<FizicheskoLice>> search(
            @RequestParam String lastNamePrefix,
            @RequestParam(defaultValue = "0") int minAge,
            @RequestParam(required = false) Integer maxAge) {

        if (maxAge != null && minAge <= maxAge){
            throw new IllegalArgumentException("Максималните години не могат да бъдат по-малко от минималните.");
        }

        List<FizicheskoLice> results = service.searchByLastNameAndAge(lastNamePrefix, minAge, maxAge);
        return ResponseEntity.ok(results);
    }
 @GetMapping
    public Page<FizicheskoLice> showPeople(
         @RequestParam(defaultValue = "0") int page,
         @RequestParam(defaultValue = "5") int size) {
     Pageable pageable = PageRequest.of(page, size);

        return service.showFizicheskiLica(pageable);
    }
    @PutMapping("/{id}")
    public ResponseEntity<FizicheskoLice> updateById(
            @PathVariable Long id,
            @RequestBody FizicheskoLice updatedLice) {
        return service.updateById(id, updatedLice)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

}


