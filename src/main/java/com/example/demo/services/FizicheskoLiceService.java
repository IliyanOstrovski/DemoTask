package com.example.demo.services;

import com.example.demo.models.FizicheskoLice;
import com.example.demo.repositories.FizicheskoLiceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FizicheskoLiceService {

    private FizicheskoLiceRepository repository;

    public FizicheskoLiceService(FizicheskoLiceRepository repository) {
        this.repository = repository;
    }

    public FizicheskoLice saveFizicheskoLice(FizicheskoLice fizicheskoLice) {
        return repository.save(fizicheskoLice);
    }

    public Optional<FizicheskoLice> getByEgn(String egn) {
        return repository.findByEgn(egn);
    }

    public List<FizicheskoLice> searchByLastNameAndAge(String lastNamePrefix, int minAge, int maxAge) {
        if (maxAge < 0) {
            throw new IllegalArgumentException("Max Age can't be under 0");
        } else {
            return repository.findByLastNameStartingWithAndAgeBetween(lastNamePrefix, minAge, maxAge);
        }
    }

    public Page<FizicheskoLice> showFizicheskiLica(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<FizicheskoLice> updateById(Long id, FizicheskoLice updatedLice) {
        return repository.findById(id).map(existingLice -> {
            existingLice.setFirstName(updatedLice.getFirstName());
            existingLice.setMiddleName(updatedLice.getMiddleName());
            existingLice.setLastName(updatedLice.getLastName());
            existingLice.setAge(updatedLice.getAge());
            existingLice.setEgn(updatedLice.getEgn());
            return repository.save(existingLice);
        });
    }
}
