package dev.dharam.userauthservice.controllers;

import dev.dharam.userauthservice.Dtos.CreateRoleRequestDto;
import dev.dharam.userauthservice.Dtos.CreateRoleResponseDto;
import dev.dharam.userauthservice.entity.Role;
import dev.dharam.userauthservice.services.roleService.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    public ResponseEntity<CreateRoleResponseDto> createRole(CreateRoleRequestDto request){
        CreateRoleResponseDto createdRole = roleService.createRole(request);
        return new ResponseEntity<>(createdRole, HttpStatus.OK);
    }
    
}
