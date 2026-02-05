package com.example.demo.controller;

import com.example.demo.model.PetDto;
import com.example.demo.model.UserDto;
import com.example.demo.service.PetService;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/pets")
public class PetController {

    private final UserService userService;
    private final PetService petService;
    //private final UserDtoMapper userDtoMapper;

    @GetMapping(path = "/{id}")
    public ResponseEntity<PetDto> getUser(@PathVariable long id) {
        //var user = userDtoMapper.map(userDto);
        return ResponseEntity.status(HttpStatus.OK).body(petService.getPet(id));
    }

    @PostMapping(path = "/add")
    public ResponseEntity<PetDto> createUser(@Valid @RequestBody PetDto petDto) {
        //var user = userDtoMapper.map(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(petService.createPet(petDto));
    }

    @PutMapping(path = "/update")
    public ResponseEntity<Long> updatePet(@Valid @RequestBody PetDto petDto) {
        //var user = userDtoMapper.map(userDto);
        return ResponseEntity.status(HttpStatus.OK).body(petService.updatePet(petDto));
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Long> deletePet(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(petService.deletePet(id));
    }

}
