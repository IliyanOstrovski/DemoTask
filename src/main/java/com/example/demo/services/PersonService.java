package com.example.demo.services;

import com.example.demo.models.Person;
import com.example.demo.repositories.PersonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public Person savePerson(Person person) {
        return repository.save(person);
    }

    public Optional<Person> getByEgn(String egn) {
        return repository.findByEgn(egn);
    }

    public List<Person> searchByFirstNameAndAge(String firstname, int minAge, int maxAge) {
        if (maxAge < 0) {
            throw new IllegalArgumentException("Max Age can't be under 0");
        } else {
            return repository.findByFirstNameAndAgeBetween(firstname, minAge, maxAge);
        }
    }

    public Page<Person> showPerson(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Person> updateById(Long id, Person updatePerson) {
        return repository.findById(id).map(existingLice -> {
            existingLice.setFirstName(updatePerson.getFirstName());
            existingLice.setMiddleName(updatePerson.getMiddleName());
            existingLice.setLastName(updatePerson.getLastName());
            existingLice.setAge(updatePerson.getAge());
            existingLice.setEgn(updatePerson.getEgn());
            return repository.save(existingLice);
        });

    }

    public boolean deleteById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

}
