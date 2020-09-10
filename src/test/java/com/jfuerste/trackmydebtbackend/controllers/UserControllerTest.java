package com.jfuerste.trackmydebtbackend.controllers;

import com.jfuerste.trackmydebtbackend.domain.User;
import com.jfuerste.trackmydebtbackend.dto.UserDTO;
import com.jfuerste.trackmydebtbackend.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest {

    public static final String NAME = "Herr Tester";
    public static final String EMAIL = "test@test.com";
    public static final long ID = 1L;
    public static final User.Role ROLE = User.Role.USER;
    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void listAll() throws Exception {
        UserDTO dto = UserDTO.builder()
                .displayName(NAME)
                .email(EMAIL)
                .id(ID)
                .role(ROLE.name())
                .build();

        when(userService.findAllAllowed(any())).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/v1/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteNonexistentUser() throws Exception {

        assertThrows(NestedServletException.class, () -> {
            mockMvc.perform(delete("/api/v1/users/2").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        });
    }

    @Test
    void deleteUser() throws Exception {
        when(userService.findUserById(anyLong())).thenReturn(new UserDTO());

        mockMvc.perform(delete("/api/v1/users/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}