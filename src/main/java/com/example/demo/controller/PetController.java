package com.example.demo.controller;

import com.example.demo.model.PetDto;
import com.example.demo.service.impl.PetServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/pets")
public class PetController {

    private final PetServiceImpl petServiceImpl;

    @GetMapping("/{id}")
    public ResponseEntity<PetDto> getPet(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(petServiceImpl.getPet(id));
    }

    @PostMapping
    public ResponseEntity<PetDto> createPet(@Valid @RequestBody PetDto petDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(petServiceImpl.createPet(petDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updatePet(@Valid @RequestBody PetDto petDto) {
        return ResponseEntity.status(HttpStatus.OK).body(petServiceImpl.updatePet(petDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deletePet(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(petServiceImpl.deletePet(id));
    }

}
