package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.UserDto;
import com.example.demo.service.inter.IUserService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
public class UserService implements IUserService {

    private final Map<Long, UserDto> userMap;// = new HashMap<>();

    public UserService() {
        this.userMap = new HashMap<>();
    }

    @Override
    public UserDto getUser(Long id) {
        return this.userMap.get(id);
    }

    @Override
    public UserDto createUser(UserDto dto) {
        Long key = (long) (this.userMap.size() + 1);
        return this.userMap.put(key, dto);
    }

    @Override
    public Long updateUser(UserDto dto) {
        UserDto user = Optional.ofNullable(this.userMap.get(dto.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(dto.getId(), "User not found", "PUT"));
        user.setName(dto.getName());
        user.setAge(dto.getAge());
        user.setEmail(dto.getEmail());
        user.setPets(dto.getPets());

        return user.getId();
    }

    @Override
    public Long deleteUser(Long id) {
        UserDto user = Optional.ofNullable(this.userMap.get(id))
                .orElseThrow(() -> new ResourceNotFoundException(id, "User not found", "DELETE"));
        return this.userMap.remove(user.getId()).getId();
    }

    public Map<Long, UserDto> getUserMap() {
        return this.userMap;
    }

}
