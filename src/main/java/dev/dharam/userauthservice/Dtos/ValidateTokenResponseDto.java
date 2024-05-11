package dev.dharam.userauthservice.Dtos;

import dev.dharam.userauthservice.entity.Role;
import dev.dharam.userauthservice.entity.SessionStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ValidateTokenResponseDto {
    private UserResponseDto userResponseDto;
    private SessionStatus sessionStatus;

}
