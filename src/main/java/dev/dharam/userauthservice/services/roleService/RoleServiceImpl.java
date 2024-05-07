package dev.dharam.userauthservice.services.roleService;

import dev.dharam.userauthservice.Dtos.CreateRoleRequestDto;
import dev.dharam.userauthservice.Dtos.CreateRoleResponseDto;
import dev.dharam.userauthservice.dtoMapper.RoleMapper;
import dev.dharam.userauthservice.entity.Role;
import dev.dharam.userauthservice.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public CreateRoleResponseDto createRole(CreateRoleRequestDto request) {
        Role role = new Role();
        role.setName(request.getName());
        roleRepository.save(role);
        CreateRoleResponseDto response = RoleMapper.convertRoleToCreateRoleResponseDto(role);
        return response;
    }
}
