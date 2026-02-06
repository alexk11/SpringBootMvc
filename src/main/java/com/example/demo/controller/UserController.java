package com.example.demo.controller;

import com.example.demo.model.UserDto;
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
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(id));
    }

    @PostMapping(path = "/add")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDto));
    }

    @PutMapping(path = "/update")
    public ResponseEntity<Long> updateUser(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userDto));
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<Long> deleteUser(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(userService.deleteUser(id));
    }

}
