package com.jfuerste.trackmydebtbackend.controllers;

import com.jfuerste.trackmydebtbackend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SignInControllerTest {


    @InjectMocks
    SignInController signInController;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(signInController).build();
    }

    @Test
    void signin() throws Exception {
        mockMvc
                .perform(post("/api/v1/signin")
                    .param("email", "test@test.com")
                    .param("password", "password"))
                .andExpect(status().isOk());
    }

    @Test
    void emailExists() throws Exception {
        mockMvc
                .perform(post("/api/v1/signin/validateEmail")
                        .param("email", "test@test.com"))
                .andExpect(status().isOk());
    }
}