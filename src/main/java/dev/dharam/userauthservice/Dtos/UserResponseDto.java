package dev.dharam.userauthservice.Dtos;

import dev.dharam.userauthservice.entity.Role;
import lombok.Getter;
import lombok.Setter;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class UserResponseDto {
    private UUID id;
    private String email;
    private Set<CreateRoleResponseDto> roles;


}
