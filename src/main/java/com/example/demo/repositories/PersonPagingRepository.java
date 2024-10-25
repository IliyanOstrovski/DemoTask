package com.example.demo.repositories;

import com.example.demo.models.Person;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PersonPagingRepository extends PagingAndSortingRepository<Person, Integer> {

    List<Person> findByFirstNameLike(String firstName, Pageable pageable);
}
