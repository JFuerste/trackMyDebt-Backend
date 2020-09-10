package com.jfuerste.trackmydebtbackend.dto.mapper;

import com.jfuerste.trackmydebtbackend.domain.User;
import com.jfuerste.trackmydebtbackend.repositories.UserRepository;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class UserQualifier {

    private final UserRepository userRepository;

    public UserQualifier(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Named("userIdToUser")
    public User idToUser(Long id){
        return userRepository.findById(id).orElse(null);
    }
}
