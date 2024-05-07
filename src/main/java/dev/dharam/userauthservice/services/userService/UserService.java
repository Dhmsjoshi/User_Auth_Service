package dev.dharam.userauthservice.services.userService;

import dev.dharam.userauthservice.Dtos.SetUserRolesRequestDto;
import dev.dharam.userauthservice.Dtos.UserDetailsResponseDto;
import dev.dharam.userauthservice.Dtos.UserResponseDto;

import java.util.UUID;

public interface UserService {
    UserDetailsResponseDto getUserDetails(UUID userId);
    UserDetailsResponseDto setUserRoles(UUID userId, SetUserRolesRequestDto rolesRequest);
}
