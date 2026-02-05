package com.example.demo.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {
    @NotNull
    private Long id;
    @NotNull
    private String name;
    @Email
    private String email;
    @Positive
    private Integer age;
    @NotNull
    private List<PetDto> pets;
}

//public record UserDto(Long id, String name, String email, Integer age, List<PetDto> pets) {}
