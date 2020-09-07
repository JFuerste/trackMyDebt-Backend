package com.jfuerste.trackmydebtbackend.dto.mapper;

import com.jfuerste.trackmydebtbackend.domain.User;
import com.jfuerste.trackmydebtbackend.dto.UserDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {

    UserMapper mapper = UserMapper.INSTANCE;


    @Test
    void userToUserDTO() {
        User user = User.builder().email("test@test.com")
                .role(User.Role.ADMIN)
                .password("secret")
                .id(1L)
                .build();

        UserDTO dto = mapper.userToUserDTO(user);

        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getEmail(), dto.getEmail());
        assertEquals(user.getRole().name(), dto.getRole());
    }
}