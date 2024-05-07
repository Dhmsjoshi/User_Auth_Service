package dev.dharam.userauthservice.dtoMapper;

import dev.dharam.userauthservice.Dtos.UserDetailsResponseDto;
import dev.dharam.userauthservice.Dtos.UserResponseDto;
import dev.dharam.userauthservice.entity.User;

public class UserMapper {
    public static UserResponseDto convertUserToUserResponseDto(User user){
        UserResponseDto response = new UserResponseDto();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        return response;
    }

    public static UserDetailsResponseDto convertUserToUserDetailResponseDto(User user){
        UserDetailsResponseDto response = new UserDetailsResponseDto();
        response.setEmail(user.getEmail());
        response.setRoles(user.getRoles());
        return response;
    }
}
