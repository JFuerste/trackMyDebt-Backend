package com.jfuerste.trackmydebtbackend.services;

import com.jfuerste.trackmydebtbackend.domain.User;
import dto.UserDTO;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.List;

public interface UserService {
    void deleteUserById(Long id);
    UserDTO findUserById(Long id);
    org.springframework.security.core.userdetails.User getUserDetails(OAuth2Authentication authentication);
    User.Role getRole(OAuth2Authentication auth);

    /**
     * Returns all users for admins and user managers, but only user details for the requesting user in case
     * in other cases.
     * @param auth Authentication token of the requesting user
     * @return List of users that the requestor is allowed to see
     */
    List<UserDTO> findAllAllowed(OAuth2Authentication auth);
}
