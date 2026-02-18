package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.UserDto;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private final Map<Long, UserDto> userMap;

    public UserServiceImpl() {
        this.userMap = new HashMap<>();
    }

    @Override
    public UserDto getUser(Long id) {
        return Optional.ofNullable(userMap.get(id))
                .orElseThrow(() -> new ResourceNotFoundException(id, "User not found", "Get user"));
    }

    @Override
    public UserDto createUser(UserDto dto) {
        if (userMap.get(dto.getId()) != null) {
            throw new BadRequestException(dto.getId(), "User already exists", "Create user");
        }
        Long userId = (long) (userMap.size() + 1);
        dto.setId(userId);
        userMap.put(userId, dto);
        return dto;
    }

    @Override
    public UserDto updateUser(UserDto dto) {
        UserDto user = Optional.ofNullable(userMap.get(dto.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(dto.getId(), "User not found", "Update user"));
        user.setName(dto.getName());
        user.setAge(dto.getAge());
        user.setEmail(dto.getEmail());
        user.setPets(dto.getPets());

        return user;
    }

    @Override
    public Long deleteUser(Long id) {
        UserDto user = Optional.ofNullable(userMap.get(id))
                .orElseThrow(() -> new ResourceNotFoundException(id, "User not found", "Delete user"));
        return userMap.remove(user.getId()).getId();
    }

    @Override
    public Map<Long, UserDto> getUserMap() {
        return userMap;
    }

}
