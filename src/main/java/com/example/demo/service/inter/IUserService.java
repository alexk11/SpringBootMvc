package com.example.demo.service.inter;

import com.example.demo.model.UserDto;


public interface IUserService {

    UserDto getUser(Long id);

    UserDto createUser(UserDto dto);

    Long updateUser(UserDto dto);

    Long deleteUser(Long id);
}
