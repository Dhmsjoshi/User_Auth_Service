package dev.dharam.userauthservice.Dtos;

import dev.dharam.userauthservice.entity.SessionStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogOutResponseDto {
    private SessionStatus sessionStatus;
}
