package dev.dharam.userauthservice.services.userService;

import dev.dharam.userauthservice.Dtos.SetUserRolesRequestDto;
import dev.dharam.userauthservice.Dtos.UserDetailsResponseDto;
import dev.dharam.userauthservice.Dtos.UserResponseDto;
import dev.dharam.userauthservice.dtoMapper.UserMapper;
import dev.dharam.userauthservice.entity.Role;
import dev.dharam.userauthservice.entity.User;
import dev.dharam.userauthservice.exceptions.UserDoesNotExistException;
import dev.dharam.userauthservice.repositories.RoleRepository;
import dev.dharam.userauthservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetailsResponseDto getUserDetails(UUID userId) {
        User savedUser = userRepository.findById(userId).orElseThrow(
                ()-> new UserDoesNotExistException("User does not exist with id: "+userId)
        );

        UserDetailsResponseDto response = UserMapper.convertUserToUserDetailResponseDto(savedUser);
        return response;
    }

    @Override
    public UserDetailsResponseDto setUserRoles(UUID userId, SetUserRolesRequestDto rolesRequest) {
        User savedUser = userRepository.findById(userId).orElseThrow(
                ()-> new UserDoesNotExistException("User does not exist with id: "+userId)
        );

        Set<Role> roles = savedUser.getRoles();
        for(Role role: rolesRequest.getRoles()){
            roles.add(role);
        }
        savedUser.setRoles(roles);
        savedUser = userRepository.save(savedUser);

        UserDetailsResponseDto response = UserMapper.convertUserToUserDetailResponseDto(savedUser);
        return response;

    }
}
