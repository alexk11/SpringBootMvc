package com.example.demo.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PetDto {
    @NotNull
    private Long id;
    @NotNull
    private String name;
    @Positive
    private Long userId;
}
