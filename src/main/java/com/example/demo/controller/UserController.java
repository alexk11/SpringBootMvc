package com.example.demo.controller;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserDtoMapper userDtoMapper;

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDto userDto) {
        var user = userDtoMapper.map(userDto);
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

}
