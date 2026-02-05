package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.PetDto;
import com.example.demo.model.UserDto;
import com.example.demo.service.inter.IPetService;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class PetService implements IPetService {

    private final UserService userService;

    public PetService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public PetDto getPet(Long id) {
        final Set<PetDto> petSet = new HashSet<>();
        this.userService.getUserMap().values().stream()
                .map(UserDto::getPets)
                .forEach(petSet::addAll);

        return petSet.stream()
                .filter(p -> (long)p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(id, "Pet not found", "Get pet"));
    }

    @Override
    public PetDto createPet(PetDto pet) {
        UserDto user = this.userService.getUserMap()
                                .values()
                                .stream()
                                .filter(u -> u.getId().compareTo(pet.getUserId()) == 0)
                                .findFirst()
                                .orElseThrow(() -> new ResourceNotFoundException(pet.getUserId(), "User not found", "Create pet"));
        pet.setId((long) (user.getPets().size() + 1));
        user.getPets().add(pet);

        return pet;
    }

    @Override
    public Long updatePet(PetDto pet) {
        this.userService.getUserMap()
                .values()
                .stream()
                .filter(u -> u.getId().compareTo(pet.getUserId()) == 0)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(pet.getUserId(), "User not found", "Update pet"));

        PetDto petDto = this.getPet(pet.getId());
        petDto.setName(pet.getName());
        petDto.setUserId(pet.getUserId());

        return pet.getId();
    }

    @Override
    public Long deletePet(Long id) {
        PetDto petDto = this.userService.getUserMap()
                .values()
                .forEach(user -> {
                    List<PetDto> pets = user.getPets();
                    for (PetDto pet : pets) {
                        if (id.compareTo(pet.getId()) == 0) {
                            return pet;
                        }
                    }
                });
    }

}
