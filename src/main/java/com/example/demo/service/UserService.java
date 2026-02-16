package com.example.demo.service;

import com.example.demo.model.UserDto;

import java.util.Map;


public interface UserService {

    UserDto createUser(UserDto dto);

    UserDto getUser(Long id);

    UserDto updateUser(UserDto dto);

    Long deleteUser(Long id);

    Map<Long, UserDto> getUserMap();
}
