package com.jfuerste.trackmydebtbackend.services;

import com.jfuerste.trackmydebtbackend.domain.User;
import com.jfuerste.trackmydebtbackend.dto.UserDTO;
import com.jfuerste.trackmydebtbackend.dto.mapper.UserMapper;
import com.jfuerste.trackmydebtbackend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    public static final String EMAIL = "test@test.com";
    public static final Long ID = 1L;
    public static final User.Role ADMIN = User.Role.ADMIN;
    @Mock
    UserRepository userRepository;
    UserServiceImpl userService;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(userRepository, UserMapper.INSTANCE);
    }

    @Test
    void loadUserByUsername() {
        String pw = new BCryptPasswordEncoder().encode("secretPW");
        User user = User.builder()
                .email(EMAIL)
                .id(ID)
                .password(pw)
                .role(ADMIN)
                .build();

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername(EMAIL);

        assertEquals(EMAIL, userDetails.getUsername());
        assertEquals(pw, userDetails.getPassword());
        assertEquals(ADMIN.name(), userDetails.getAuthorities().stream().findFirst().get().getAuthority());
        verify(userRepository).findByEmail(anyString());
    }

    @Test
    void deleteUserById() {
        userService.deleteUserById(ID);
    }

    @Test
    void findUserById() {
        String pw = new BCryptPasswordEncoder().encode("secretPW");
        User user = User.builder()
                .email(EMAIL)
                .id(ID)
                .password(pw)
                .role(ADMIN)
                .build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        UserDTO dto = userService.findUserById(ID);

        assertEquals(EMAIL, dto.getEmail());
        assertEquals(ID, dto.getId());
        verify(userRepository).findById(anyLong());
    }


    @Test
    void findAllAllowedAdmin() {
        org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(
                "name", "pw", List.of(new SimpleGrantedAuthority("ADMIN"))
        );

        OAuth2Authentication auth = mock(OAuth2Authentication.class);
        when(auth.getPrincipal()).thenReturn(user);
        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        userService.findAllAllowed(auth);

        verify(userRepository).findAll();


    }

    @Test
    void findAllAllowedUser() {
        org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(
                "name", "pw", List.of(new SimpleGrantedAuthority("USER"))
        );

        OAuth2Authentication auth = mock(OAuth2Authentication.class);
        when(auth.getPrincipal()).thenReturn(user);
        when(userRepository.findAllByEmail(anyString())).thenReturn(new ArrayList<>());

        userService.findAllAllowed(auth);

        verify(userRepository).findAllByEmail(anyString());


    }

    @Test
    void getRole() {
        org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(
                "name", "pw", List.of(new SimpleGrantedAuthority("USER"))
        );

        OAuth2Authentication auth = mock(OAuth2Authentication.class);
        when(auth.getPrincipal()).thenReturn(user);

        assertEquals(User.Role.USER, userService.getRole(auth));
    }
}