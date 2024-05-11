package dev.dharam.userauthservice.services.roleService;


import dev.dharam.userauthservice.Dtos.CreateRoleRequestDto;
import dev.dharam.userauthservice.Dtos.CreateRoleResponseDto;
import dev.dharam.userauthservice.entity.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

public interface RoleService {
    CreateRoleResponseDto  createRole(CreateRoleRequestDto request);
    String setUserRoleAdmin(UUID userId);
}
