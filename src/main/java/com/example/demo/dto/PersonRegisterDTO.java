package com.example.demo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PersonRegisterDTO {

    @NotBlank(message = "Собственото име е задължително")
    @Pattern(regexp = "^[A-Za-zА-Яа-я\\-]+$", message = "Собственото име не може да съдържа числа!")
    private String firstName;

    @NotBlank(message = "Бащиното име е задължително")
    @Pattern(regexp = "^[A-Za-zА-Яа-я]+$", message = "Бащиното име не може да съдържа числа!")
    private String middleName;

    @NotBlank(message = "Фамилията е задължителна")
    @Pattern(regexp = "^[A-Za-zА-Яа-я]+$", message = "Фамилията не може да съдържа числа!")
    private String lastName;

    @Pattern(regexp = "\\d{10}", message = "ЕГН-то трябва да съдържа 10 цифри!")
    private String egn;

    @Min(value = 0, message = "Възрастта трябва да бъде положително число!")
    @Max(value = 120, message = "Възрастта трябва да бъде по-малка или равна на 120!")
    private int age;


}
