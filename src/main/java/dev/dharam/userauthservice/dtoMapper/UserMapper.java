package dev.dharam.userauthservice.dtoMapper;

import dev.dharam.userauthservice.Dtos.CreateRoleResponseDto;
import dev.dharam.userauthservice.Dtos.UserDetailsResponseDto;
import dev.dharam.userauthservice.Dtos.UserResponseDto;
import dev.dharam.userauthservice.entity.Role;
import dev.dharam.userauthservice.entity.User;

import java.util.HashSet;
import java.util.Set;

public class UserMapper {
    public static UserResponseDto convertUserToUserResponseDto(User user){
        UserResponseDto response = new UserResponseDto();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        if(user.getRoles() != null){
            Set<CreateRoleResponseDto> roleResponseDtos = new HashSet<>();
            for(Role role: user.getRoles()){
                CreateRoleResponseDto roleDto = new CreateRoleResponseDto();
                roleDto.setName(role.getName());
                roleResponseDtos.add(roleDto);
            }
            response.setRoles(roleResponseDtos);
        }else{
            response.setRoles(new HashSet<>());
        }

        return response;
    }

    public static UserDetailsResponseDto convertUserToUserDetailResponseDto(User user){
        UserDetailsResponseDto response = new UserDetailsResponseDto();
        response.setEmail(user.getEmail());
        response.setRoles(user.getRoles());
        return response;
    }
}
