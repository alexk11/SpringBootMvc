package com.example.demo.service.inter;

import com.example.demo.model.PetDto;


public interface IPetService {

    PetDto getPet(Long id);

    PetDto createPet(PetDto dto);

    Long updatePet(PetDto dto);

    Long deletePet(Long id);
}
