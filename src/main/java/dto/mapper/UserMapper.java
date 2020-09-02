package dto.mapper;

import com.jfuerste.trackmydebtbackend.domain.User;
import dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userToUserDTO(User user);
    default String roleToString(User.Role role) {
        return role.name();
    };
}
