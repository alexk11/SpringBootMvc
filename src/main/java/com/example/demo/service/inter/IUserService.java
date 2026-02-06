package com.example.demo.service.inter;

import com.example.demo.model.UserDto;


public interface IUserService {

    UserDto createUser(UserDto dto);

    UserDto getUser(Long id);

    Long updateUser(UserDto dto);

    Long deleteUser(Long id);
}
