package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.PetDto;
import com.example.demo.model.UserDto;
import com.example.demo.service.PetService;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class PetServiceImpl implements PetService {

    private final UserService userService;

    public PetServiceImpl(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public PetDto getPet(Long id) {
        return getAllPets()
                .stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(id, "Pet not found", "Get pet"));
    }

    @Override
    public PetDto createPet(PetDto pet) {
        UserDto user = userService.getUserMap()
                        .values()
                        .stream()
                        .filter(u -> u.getId().equals(pet.getUserId()))
                        .findFirst()
                        .orElseThrow(() -> new ResourceNotFoundException(pet.getUserId(), "User not found", "Create pet"));
        pet.setId((long)(getAllPets().size() + 1));
        user.getPets().add(pet);
        return pet;
    }

    @Override
    public Long updatePet(PetDto pet) {
        PetDto foundPet = getPet(pet.getId());
        // pet owner has changed
        if (!foundPet.getUserId().equals(pet.getUserId())) {
            // remove pet from previous owner
            userService.getUserMap().values().stream()
                .filter(u -> u.getId().equals(foundPet.getUserId()))
                .findFirst()
                .map(u -> u.getPets().remove(foundPet))
                .orElseThrow(() -> new ResourceNotFoundException(pet.getUserId(), "Current pet owner not found", "Update pet"));
            // add pet to new owner
            foundPet.setName(pet.getName());
            foundPet.setUserId(pet.getUserId());
            userService.getUserMap().values().stream()
                .filter(u -> u.getId().equals(foundPet.getUserId()))
                .findFirst()
                .map(u -> u.getPets().add(foundPet))
                .orElseThrow(() -> new ResourceNotFoundException(pet.getUserId(), "New pet owner not found", "Update pet"));
        } else {
            foundPet.setName(pet.getName());
            foundPet.setUserId(pet.getUserId());
        }
        return foundPet.getId();
    }

    @Override
    public Long deletePet(Long id) {
        // collect all pet id
        final Set<Long> petIds = new HashSet<>();
        userService.getUserMap().values().stream()
            .map(UserDto::getPets)
            .forEach(pets -> {
                for (PetDto pet : pets) {
                    petIds.add(pet.getId());
                }
            });
        // raise exception if the pet with id doesn't exist
        if (!petIds.contains(id)) {
            throw new ResourceNotFoundException(id, "Pet not found", "Delete pet");
        }
        // iterate users and remove the pet
        userService.getUserMap()
            .values()
            .forEach(user -> user.getPets().removeIf(pet -> id.equals(pet.getId())));
        return id;
    }

    /**
     * Get all Pets from all Users
     */
    private Set<PetDto> getAllPets() {
        final Set<PetDto> petSet = new HashSet<>();
        userService.getUserMap().values().stream()
            .map(UserDto::getPets)
            .forEach(petSet::addAll);
        return petSet;
    }

}
