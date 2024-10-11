package com.example.demo.services;

import com.example.demo.models.FizicheskoLice;
import com.example.demo.repositories.FizicheskoLiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FizicheskoLiceService {

    private final FizicheskoLiceRepository repository;

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
            return repository.findByLastNameStartingWithAndAgeGreaterThan(lastNamePrefix, minAge);
        } else {
            return repository.findByLastNameStartingWithAndAgeBetween(lastNamePrefix, minAge, maxAge);
        }
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

    // Първоначално зареждане с данни
   /* @EventListener(ApplicationReadyEvent.class)
    public void loadData() {
        if (repository.count() == 0) {
            List<FizicheskoLice> initialLica = List.of(
                    new FizicheskoLice("Иван", "Георгиев", "Иванов", "1234567890", 30),
                    new FizicheskoLice("Георги", "Иванов", "Георгиев", "2345678901", 40),
                    new FizicheskoLice("Мария", "Петрова", "Димитрова", "3456789012", 25),
                    new FizicheskoLice("Александър", "Стоянов", "Александров", "4567890123", 35),
                    new FizicheskoLice("Елена", "Димитрова", "Георгиева", "5678901234", 28),
                    new FizicheskoLice("Костадин", "Георгиев", "Костадинов", "6789012345", 45),
                    new FizicheskoLice("Николай", "Ангелов", "Николов", "7890123456", 22),
                    new FizicheskoLice("Анна", "Мартинова", "Станкова", "8901234567", 33),
                    new FizicheskoLice("Димитър", "Тодоров", "Димитров", "9012345678", 55),
                    new FizicheskoLice("Петя", "Ангелова", "Петкова", "0123456789", 29),
                    new FizicheskoLice("Светослав", "Костов", "Светославов", "0987654321", 41),
                    new FizicheskoLice("Даниела", "Петрова", "Иванова", "1357924680", 31),
                    new FizicheskoLice("Станимир", "Димов", "Станимиров", "2468135790", 36),
                    new FizicheskoLice("Таня", "Николова", "Николова", "3579246801", 27),
                    new FizicheskoLice("Габриела", "Иванова", "Габриелова", "4681357920", 24),
                    new FizicheskoLice("Владимир", "Радев", "Владимиров", "5792468130", 38),
                    new FizicheskoLice("Нина", "Петрова", "Нинова", "6803579241", 26),
                    new FizicheskoLice("Симеон", "Тодоров", "Симеонов", "7914681352", 50),
                    new FizicheskoLice("Радослав", "Станев", "Радославов", "8025792463", 32),
                    new FizicheskoLice("Ирина", "Ковачева", "Ириева", "9136803574", 23),
                    new FizicheskoLice("Мартин", "Маринов", "Мартинов", "0247914685", 34)
            );
            repository.saveAll(initialLica);
        }
    }
   */
}
