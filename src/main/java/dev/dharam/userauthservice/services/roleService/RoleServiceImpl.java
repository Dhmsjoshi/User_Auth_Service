package dev.dharam.userauthservice.services.roleService;

import dev.dharam.userauthservice.Dtos.CreateRoleRequestDto;
import dev.dharam.userauthservice.Dtos.CreateRoleResponseDto;
import dev.dharam.userauthservice.dtoMapper.RoleMapper;
import dev.dharam.userauthservice.entity.Role;
import dev.dharam.userauthservice.entity.User;
import dev.dharam.userauthservice.exceptions.UserAlreadyExistsException;
import dev.dharam.userauthservice.exceptions.UserDoesNotExistException;
import dev.dharam.userauthservice.repositories.RoleRepository;
import dev.dharam.userauthservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public CreateRoleResponseDto createRole(CreateRoleRequestDto request) {
        Role role = new Role();
        role.setName(request.getName());
        roleRepository.save(role);
        CreateRoleResponseDto response = RoleMapper.convertRoleToCreateRoleResponseDto(role);
        return response;
    }

    public String setUserRoleAdmin( @PathVariable("id") UUID userId){
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new UserDoesNotExistException("User does not exist with id: "+userId);
        }

        Role role = roleRepository.findByName("ADMIN").orElseThrow(
                ()->new RuntimeException("Unknown error")
        );

        User user = userOptional.get();
        Set<Role> roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);
        user = userRepository.save(user);
        return "Success";

    }
}
