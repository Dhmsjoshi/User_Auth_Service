package dev.dharam.userauthservice.Dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class LogOutRequestDto {
    private String token;
    private UUID userId;
}
