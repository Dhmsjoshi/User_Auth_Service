package dev.dharam.userauthservice.services.authService;

import dev.dharam.userauthservice.Dtos.*;
import dev.dharam.userauthservice.entity.Session;

import java.util.Optional;
import java.util.UUID;


public interface AuthService {
    UserResponseDto signUp(SignUpRequestDto request);
    UserResponseDto logIn(LogInRequestDto request);
    LogOutResponseDto logOut(LogOutRequestDto request);
    Optional<ValidateTokenResponseDto> validateToken(ValidateTokenRequestDto request);
    Session createSession(String token, UUID userId);

}
