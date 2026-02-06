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
        return this.getAllPets()
                .stream()
                .filter(p -> (long)p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(id, "Pet with not found", "Get pet"));
    }

    @Override
    public PetDto createPet(PetDto pet) {
        UserDto user = this.userService.getUserMap()
                                .values()
                                .stream()
                                .filter(u -> u.getId().compareTo(pet.getUserId()) == 0)
                                .findFirst()
                                .orElseThrow(() -> new ResourceNotFoundException(pet.getUserId(), "User not found", "Create pet"));
        pet.setId((long) (this.getAllPets().size() + 1));
        user.getPets().add(pet);
        return pet;
    }

    @Override
    public Long updatePet(PetDto pet) {
        PetDto foundPet = this.getPet(pet.getId());
        // pet owner has changed
        if (foundPet.getUserId().compareTo(pet.getUserId()) != 0) {
            // remove pet from the previous owner
            this.userService.getUserMap().values().stream()
                .filter(u -> u.getId().compareTo(foundPet.getUserId()) == 0)
                .findFirst()
                .map(u -> u.getPets().remove(foundPet))
                .orElseThrow(() -> new ResourceNotFoundException(pet.getUserId(), "Current pet owner not found", "Update pet"));
            // add pet to the new pet owner
            foundPet.setName(pet.getName());
            foundPet.setUserId(pet.getUserId());
            this.userService.getUserMap().values().stream()
                .filter(u -> u.getId().compareTo(foundPet.getUserId()) == 0)
                .findFirst()
                .map(u -> u.getPets().add(foundPet))
                .orElseThrow(() -> new ResourceNotFoundException(pet.getUserId(), "New pet owner not found", "Update pet"));
        }
        return foundPet.getId();
    }

    @Override
    public Long deletePet(Long id) {
        // collect all pet id
        final Set<Long> petIds = new HashSet<>();
        this.userService.getUserMap().values().stream()
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
        this.userService.getUserMap()
            .values()
            .forEach(user -> {
                user.getPets().removeIf(pet -> id.compareTo(pet.getId()) == 0);
            });
        return id;
    }

    /**
     * Get all Pets from all Users
     */
    private Set<PetDto> getAllPets() {
        final Set<PetDto> petSet = new HashSet<>();
        this.userService.getUserMap().values().stream()
                .map(UserDto::getPets)
                .forEach(petSet::addAll);
        return petSet;
    }

}
