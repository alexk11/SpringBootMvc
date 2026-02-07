package com.example.demo.model;

import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private Long id;
    @NotNull
    private String name;
    @Positive
    private Long userId;
}
