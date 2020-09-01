package com.jfuerste.trackmydebtbackend.services;

import com.jfuerste.trackmydebtbackend.domain.User;
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

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
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
    public User findUserById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public org.springframework.security.core.userdetails.User getUserDetails(OAuth2Authentication authentication) {
        org.springframework.security.core.userdetails.User auth =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        return auth;
    }

    @Override
    public User.Role getRole(OAuth2Authentication auth) {
        org.springframework.security.core.userdetails.User userDetails = getUserDetails(auth);
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
    public List<User> findAllAllowed(OAuth2Authentication auth) {
        org.springframework.security.core.userdetails.User userDetails = getUserDetails(auth);
        String role = userDetails.getAuthorities().stream().findFirst().get().getAuthority();

        List<User> allowedUsers;
        if (role.equals(User.Role.USER.name())){
            allowedUsers = repository.findAllByEmail(userDetails.getUsername());
        } else {
            allowedUsers = repository.findAll();
        }
        return allowedUsers;
    }
}
