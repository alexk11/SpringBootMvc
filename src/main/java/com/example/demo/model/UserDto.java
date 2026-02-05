package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private List<PetDto> pets;
}

//public record UserDto(Long id, String name, String email, Integer age, List<PetDto> pets) {}
