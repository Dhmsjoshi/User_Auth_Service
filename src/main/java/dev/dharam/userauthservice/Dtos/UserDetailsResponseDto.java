package dev.dharam.userauthservice.Dtos;

import dev.dharam.userauthservice.entity.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserDetailsResponseDto {
    private String email;
    private Set<Role> roles = new HashSet<>();
}
