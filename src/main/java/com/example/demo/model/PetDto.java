package com.example.demo.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class PetDto {
    private Long id;
    @NotBlank(message = "Имя питомца обязательно для заполнения")
    private String name;
    @Positive
    private Long userId;
}
