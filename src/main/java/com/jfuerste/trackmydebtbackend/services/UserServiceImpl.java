package com.jfuerste.trackmydebtbackend.services;

import com.jfuerste.trackmydebtbackend.domain.User;
import com.jfuerste.trackmydebtbackend.dto.UserDTO;
import com.jfuerste.trackmydebtbackend.dto.mapper.UserMapper;
import com.jfuerste.trackmydebtbackend.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository repository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository repository, UserMapper userMapper) {
        this.repository = repository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found: " + username));
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Collections.singletonList(authority));
    }

    @Override
    public void deleteUserById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public UserDTO findUserById(Long id) {
        User user = repository.findById(id).orElse(null);
        return userMapper.userToUserDTO(user);

    }

    public org.springframework.security.core.userdetails.User getSpringUserDetails(OAuth2Authentication authentication) {
        org.springframework.security.core.userdetails.User auth =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        return auth;
    }

    @Override
    public User getUser(OAuth2Authentication auth2Authentication) {
        org.springframework.security.core.userdetails.User principal =
                (org.springframework.security.core.userdetails.User) auth2Authentication.getPrincipal();
        String email = principal.getUsername();
        return repository.findByEmail(email).orElse(null);
    }

    @Override
    public User.Role getRole(OAuth2Authentication auth) {
        org.springframework.security.core.userdetails.User userDetails = getSpringUserDetails(auth);
        String role = userDetails.getAuthorities().stream().findFirst().get().getAuthority();

        if (role.equals(User.Role.ADMIN.name())){
            return User.Role.ADMIN;
        } else if (role.equals(User.Role.USER_MANAGER.name())) {
            return User.Role.USER_MANAGER;
        } else {
            return User.Role.USER;
        }
    }


    @Override
    public List<UserDTO> findAllAllowed(OAuth2Authentication auth) {
        org.springframework.security.core.userdetails.User userDetails = getSpringUserDetails(auth);
        String role = userDetails.getAuthorities().stream().findFirst().get().getAuthority();

        List<User> allowedUsers;
        if (role.equals(User.Role.USER.name())){
            allowedUsers = repository.findAllByEmail(userDetails.getUsername());
        } else {
            allowedUsers = repository.findAll();
        }
        return allowedUsers.stream()
                .map(userMapper::userToUserDTO)
                .collect(Collectors.toList());
    }
}
