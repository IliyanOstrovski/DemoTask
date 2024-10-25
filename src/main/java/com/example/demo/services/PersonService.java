package com.example.demo.services;

import com.example.demo.models.Person;
import com.example.demo.repositories.PersonPagingRepository;
import com.example.demo.repositories.PersonRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
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

    public List<Person> findPersonByFirstNameStartWith(String firstName, Pageable pageable) {
        return personPagingRepository.findByFirstNameStartingWith(firstName, pageable);
    }

    public List<Person> findAllFirstNameAndAgeAndEgn(String firstName, Integer minAge, String egn) {
        return repository.findByFirstNameAndAgeOrEgn(firstName, minAge, egn);
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
