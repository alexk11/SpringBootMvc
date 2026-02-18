package com.example.demo;

import com.example.demo.model.PetDto;
import com.example.demo.model.UserDto;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.PetServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@AutoConfigureMockMvc
@SpringBootTest
class PetServiceTest {

    @Autowired
    private PetServiceImpl petService;

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldCreateNewPet() throws Exception {
        var userDto = new UserDto(1L,
                "Alex",
                "test@example.com",
                35,
                new ArrayList<>());

        var petDto = new PetDto(1L,
                "Jack",
                1L);

        userService.getUserMap().put(1L, userDto);

        String newPetJson = objectMapper.writeValueAsString(petDto);

        var jsonResponse = mockMvc.perform(post("/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newPetJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var petDtoResponse = objectMapper.readValue(jsonResponse, PetDto.class);

        Assertions.assertEquals(petDto.getId(), petDtoResponse.getId());
        Assertions.assertEquals(petDto.getName(), petDtoResponse.getName());
        Assertions.assertEquals(petDto.getUserId(), petDtoResponse.getUserId());
        Assertions.assertDoesNotThrow(() -> petService.getPet(petDtoResponse.getId()));
    }

    @Test
    public void shouldGetPetById() throws Exception {
        var petDto = new PetDto(1L,
                "Jack",
                1L);

        var userDto = new UserDto(1L,
                "Alex",
                "test@example.com",
                35,
                List.of(petDto));

        userService.getUserMap().put(1L, userDto);

        var jsonResponse = mockMvc.perform(get("/pets/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var petDtoResponse = objectMapper.readValue(jsonResponse, PetDto.class);

        Assertions.assertEquals(petDto.getId(), petDtoResponse.getId());
        Assertions.assertEquals(petDto.getName(), petDtoResponse.getName());
        Assertions.assertEquals(petDto.getUserId(), petDtoResponse.getUserId());
        Assertions.assertDoesNotThrow(() -> petService.getPet(petDtoResponse.getId()));
    }

}

