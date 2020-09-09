package com.jfuerste.trackmydebtbackend.controllers;


import com.jfuerste.trackmydebtbackend.domain.User;
import com.jfuerste.trackmydebtbackend.dto.UserDTO;
import com.jfuerste.trackmydebtbackend.dto.mapper.UserMapper;
import com.jfuerste.trackmydebtbackend.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1/signin")
public class SignInController {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper;

    public SignInController(UserRepository repository, PasswordEncoder passwordEncoder, UserMapper mapper) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    @PostMapping
    UserDTO signin(@RequestParam String email, @RequestParam String password) {
        //User u = new User(null, email, passwordEncoder.encode(password), User.Role.USER);
        User u = User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(User.Role.USER)
                .build();
        repository.save(u);
        return mapper.userToUserDTO(u);
    }

    @PostMapping("/api/v1/validateEmail")
    Boolean emailExists(@RequestParam String email) {
        return repository.existsByEmail(email);
    }

}