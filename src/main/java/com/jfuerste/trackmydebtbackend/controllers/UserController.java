package com.jfuerste.trackmydebtbackend.controllers;

import com.jfuerste.trackmydebtbackend.domain.User;
import com.jfuerste.trackmydebtbackend.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/v1/users")
@Validated
@Slf4j
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    ResponseEntity<List<User>> all(UsernamePasswordAuthenticationToken authentication){
        org.springframework.security.core.userdetails.User auth = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        String role = authentication.getAuthorities().stream().findFirst().get().getAuthority();
        if (role.equals(User.Role.USER.name())){
            List<User> allByEmail = userRepository.findAllByEmail(auth.getUsername());
            return new ResponseEntity(allByEmail, HttpStatus.OK);
        }
        return new ResponseEntity(userRepository.findAll(), HttpStatus.OK);
    }
}
