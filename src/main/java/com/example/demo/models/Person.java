package com.example.demo.models;


import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "fizicheski_lica")
    public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    @NotBlank(message = "Собственото име е задължително")
    @Pattern(regexp = "^[A-Za-zА-Яа-я\\-]+$", message = "Собственото име не може да съдържа числа!")
    private String firstName;
    @Column
    @NotBlank(message = "Бащиното име е задължително")
    @Pattern(regexp = "^[A-Za-zА-Яа-я]", message = "Бащиното име не може да съдържа числа!")
    private String middleName;
    @Column
    @NotBlank(message = "Фамилията е задължителна")
    @Pattern(regexp = "^[A-Za-zА-Яа-я]", message = "Фамилията не може да съдържа числа!")
    private String lastName;
    @Column(unique = true)
    @Pattern(regexp = "\\d{10}", message = "ЕГН-то трябва да съдържа 10 цифри!")
    private String egn;
    @Column
    @Min(value = 0, message = "Възрастта трябва да бъде положително число!")
    private int age;

}


