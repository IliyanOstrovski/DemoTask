package com.example.demo.repositories;

import com.example.demo.models.FizicheskoLice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface FizicheskoLiceRepository extends JpaRepository<FizicheskoLice, Long> {
    Optional<FizicheskoLice> findByEgn(String egn);
    List<FizicheskoLice> findByLastNameStartingWithAndAgeGreaterThan(String lastNamePrefix, int age);
    List<FizicheskoLice> findByLastNameStartingWithAndAgeBetween(String lastNamePrefix, int ageStart, int ageEnd);

}

