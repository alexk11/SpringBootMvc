package com.example.demo.service;

import com.example.demo.model.PetDto;


public interface PetService {

    PetDto createPet(PetDto dto);

    PetDto getPet(Long id);

    Long updatePet(PetDto dto);

    Long deletePet(Long id);
}
