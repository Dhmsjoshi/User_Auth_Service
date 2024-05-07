package dev.dharam.userauthservice.Dtos;

import dev.dharam.userauthservice.entity.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class SetUserRolesRequestDto {
    private Set<Role> roles;
}
