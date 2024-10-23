package com.example.demo.repositories;

import com.example.demo.models.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

    Optional<Person> findByEgn(String egn);
    List<Person> findByLastNameStartingWithAndAgeGreaterThan(String lastNamePrefix, int age);
    List<Person> findByFirstNameAndAgeBetween(String firstName, int ageStart, int ageEnd);
    Page<Person> findAll(Pageable pageable);

    }


