package com.example.demo.service.inter;

import com.example.demo.model.PetDto;


public interface IPetService {

    PetDto createPet(PetDto dto);

    PetDto getPet(Long id);

    Long updatePet(PetDto dto);

    Long deletePet(Long id);
}
