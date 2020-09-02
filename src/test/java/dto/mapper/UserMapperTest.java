package dto.mapper;

import com.jfuerste.trackmydebtbackend.domain.User;
import com.jfuerste.trackmydebtbackend.dto.UserDTO;
import com.jfuerste.trackmydebtbackend.dto.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserMapperTest {

    public static final String EMAIL = "test@test.com";
    public static final Long ID = 1L;
    public static final User.Role ADMIN = User.Role.ADMIN;

    @Test
    void userToUserDTO() {
        String pw = new BCryptPasswordEncoder().encode("secretPW");
        User user = User.builder()
                .email(EMAIL)
                .id(ID)
                .password(pw)
                .role(ADMIN)
                .build();

        UserMapper mapper = UserMapper.INSTANCE;

        UserDTO userDTO = mapper.userToUserDTO(user);

        assertEquals(EMAIL, userDTO.getEmail());
        assertEquals(ID, userDTO.getId());
        assertEquals(ADMIN.name(), userDTO.getRole());
    }
}