package com.example.filmservice.controller;

import com.example.filmservice.model.AppUser;
import com.example.filmservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testRegisterUserSuccess() throws Exception {
        // Skapa en användare
        AppUser user = new AppUser();
        user.setUsername("testuser");
        user.setPassword("password123");

        // Mocka `userService.createUser` för att returnera en användare
        when(userService.createUser(any(AppUser.class))).thenReturn(user);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testuser\", \"password\": \"password123\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("User testuser registered successfully!"));
    }

    @Test
    public void testRegisterUserUsernameExists() throws Exception {
        AppUser existingUser = new AppUser();
        existingUser.setUsername("existingUser");

        // Mocka `userService.getUserByUsername` för att returnera en användare som redan finns
        when(userService.getUserByUsername("existingUser")).thenReturn(Optional.of(existingUser));

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"existingUser\", \"password\": \"password123\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Username already exists!"));
    }
}