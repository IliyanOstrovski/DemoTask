package com.example.demo.services;

import com.example.demo.models.Person;
import com.example.demo.repositories.PersonPagingRepository;
import com.example.demo.repositories.PersonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository repository;
    private final PersonPagingRepository personPagingRepository;

    public PersonService(PersonRepository repository, PersonPagingRepository personPagingRepository) {
        this.repository = repository;
        this.personPagingRepository = personPagingRepository;
    }

    public Person savePerson(Person person) {
        return repository.save(person);
    }

    public List<Person> findPersonByFirstName(String firstName, Pageable pageable) {
        return personPagingRepository.findByFirstNameLike(firstName+"%", pageable);
    }

    public List<Person> findAllFirstNameAndAgeAndEgn(String firstName, Integer minAge, String egn) {
        return repository.findByFirstNameAndAgeOrEgn(firstName, minAge, egn);
    }

    public List<Person> searchByAgeRange(int minAge, int maxAge) {
        return repository.findByAgeBetween(minAge, maxAge);
    }

    public List<Person> saveAllPersons(List<Person> persons) {
        return (List<Person>) repository.saveAll(persons); // This saves all persons and returns a list
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
