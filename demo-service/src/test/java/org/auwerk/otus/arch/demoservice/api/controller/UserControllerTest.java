package org.auwerk.otus.arch.demoservice.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.auwerk.otus.arch.demoservice.api.dto.User;
import org.auwerk.otus.arch.demoservice.entity.UserEntity;
import org.auwerk.otus.arch.demoservice.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void resetDb() {
        userRepository.deleteAll();
    }

    @Test
    void createUser() throws Exception {
        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(buildUser("user"))))
                .andExpect(status().isOk());

        assertEquals(1, userRepository.count());
    }

    @Test
    void createUserDuplicate() throws Exception {
        UserEntity userEntity = UserEntity.fromDto(buildUser("user"));
        userRepository.save(userEntity);

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(buildUser("user"))))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void deleteUser() throws Exception {
        UserEntity userEntity = UserEntity.fromDto(buildUser("user"));
        userRepository.save(userEntity);

        mockMvc.perform(MockMvcRequestBuilders.delete("/user/" + userEntity.getId()))
                .andExpect(status().isNoContent());

        assertEquals(0, userRepository.count());
    }

    @Test
    void deleteNonExistingUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/" + 1L))
                .andExpect(status().isNotFound());
    }

    private static User buildUser(String userName) {
        return new User(null, userName, userName, userName, userName + "@user.ru", "999999");
    }
}
