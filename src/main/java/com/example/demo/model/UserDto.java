package com.example.demo.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    @NotBlank(message = "Имя пользователя обязательно для заполнения")
    private String name;
    @Email(message = "Некорректный формат email")
    private String email;
    @Positive
    private Integer age;
    @NotNull
    private List<PetDto> pets;
}
