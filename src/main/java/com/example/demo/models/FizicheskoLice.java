package com.example.demo.models;


import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "fizicheski_lica")
    public class FizicheskoLice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
        @NotBlank(message = "Собственото име е задължително")
        private String firstName;
    @Column
        @NotBlank(message = "Бащиното име е задължително")
        private String middleName;
    @Column
        @NotBlank(message = "Фамилията е задължителна")
        private String lastName;
    @Column(unique = true)
        @Pattern(regexp = "\\d{10}", message = "ЕГН-то трябва да съдържа 10 цифри")
        private String egn;
    @Column
        @Min(value = 0, message = "Възрастта трябва да бъде положително число")
        private int age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEgn() {
        return egn;
    }

    public void setEgn(String egn) {
        this.egn = egn;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public FizicheskoLice() {}

    public FizicheskoLice(String firstName, String middleName, String lastName, String egn, int age) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.egn = egn;
        this.age = age;
    }


}


