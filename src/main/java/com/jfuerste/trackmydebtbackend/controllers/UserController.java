package com.jfuerste.trackmydebtbackend.controllers;

import com.jfuerste.trackmydebtbackend.dto.UserDTO;
import com.jfuerste.trackmydebtbackend.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/v1/users")
@Validated
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    ResponseEntity<List<UserDTO>> listAll(OAuth2Authentication authentication){
        return new ResponseEntity(userService.findAllAllowed(authentication), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<UserDTO> deleteUser(@PathVariable Long id, OAuth2Authentication authentication){
        UserDTO user = userService.findUserById(id);
        if (user != null){
            userService.deleteUserById(id);
        } else {
            throw new RuntimeException("User not found");
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }


}
