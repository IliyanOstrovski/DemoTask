package com.example.demo.repositories;

import com.example.demo.models.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

    List<Person> findByFirstNameAndAgeOrEgn(@Param("firstName") String firstName,
                                            @Param("minAge") Integer minAge,
                                            @Param("egn") String egn);
    Optional<Person> findByEgn(String egn);
    List<Person> findAllByFirstNameAndAgeAndEgn(String firstName, int age, String egn);
    List<Person> findByAgeBetween(int ageStart, int ageEnd);
    Page<Person> findAllById(Long id, Pageable pageable);


}



