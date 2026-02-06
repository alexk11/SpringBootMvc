package com.example.demo;

import com.example.demo.model.UserDto;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldCreateNewUser() throws Exception {
        var userDto = new UserDto(0L,
                "Pavel",
                "test@example.com",
                25,
                List.of());

        String newUserJson = objectMapper.writeValueAsString(userDto);

        var jsonResponse = mockMvc.perform(post("/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var userDtoResponse = objectMapper.readValue(jsonResponse, UserDto.class);

        Assertions.assertEquals(userDto.getId(), userDtoResponse.getId());
        Assertions.assertEquals(userDto.getName(), userDtoResponse.getName());
        Assertions.assertEquals(userDto.getAge(), userDtoResponse.getAge());
        Assertions.assertEquals(userDto.getEmail(), userDtoResponse.getEmail());
        Assertions.assertEquals(userDto.getPets(), userDtoResponse.getPets());
        Assertions.assertDoesNotThrow(() -> userService.getUser(userDtoResponse.getId()));
    }

    @Test
    public void shouldGetUserById() throws Exception {

        var userDto = new UserDto(1L,
                "Alex",
                "test@example.com",
                35,
                List.of());

        String userJson = objectMapper.writeValueAsString(userDto);

        when(userService.getUser(anyLong())).thenReturn(userDto);

        var jsonResponse = mockMvc.perform(get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var userDtoResponse = objectMapper.readValue(jsonResponse, UserDto.class);

        Assertions.assertEquals(userDto.getId(), userDtoResponse.getId());
        Assertions.assertEquals(userDto.getName(), userDtoResponse.getName());
        Assertions.assertEquals(userDto.getAge(), userDtoResponse.getAge());
        Assertions.assertEquals(userDto.getEmail(), userDtoResponse.getEmail());
        Assertions.assertEquals(userDto.getPets(), userDtoResponse.getPets());
        Assertions.assertDoesNotThrow(() -> userService.getUser(userDtoResponse.getId()));
    }

}
