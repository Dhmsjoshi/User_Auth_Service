package dev.dharam.userauthservice.services.roleService;


import dev.dharam.userauthservice.Dtos.CreateRoleRequestDto;
import dev.dharam.userauthservice.Dtos.CreateRoleResponseDto;
import dev.dharam.userauthservice.entity.Role;
import org.springframework.http.ResponseEntity;

public interface RoleService {
    CreateRoleResponseDto  createRole(CreateRoleRequestDto request);
}
