package com.example.demo.dto;


import com.example.demo.validations.ValidAgeRange;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;


@Setter
@Getter
@ValidAgeRange
public class PersonSearchDTO {
    // Getters and setters
    private String firstName;
    @Min(value = 0, message = "Минималната възраст не може да бъде отрицателна")
    private int minAge = 0; // Default value
    @Min(value = 0, message = "Максималната възраст не може да бъде отрицателна")
    private Integer maxAge;

    private int page = 0;  // Default page number
    private int size = 20; // Default page size

}