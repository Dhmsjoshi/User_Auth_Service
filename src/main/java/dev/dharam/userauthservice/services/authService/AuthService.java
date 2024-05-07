package dev.dharam.userauthservice.services.authService;

import dev.dharam.userauthservice.Dtos.*;
import dev.dharam.userauthservice.entity.Session;
import dev.dharam.userauthservice.entity.User;
import org.springframework.stereotype.Service;

import java.util.UUID;


public interface AuthService {
    UserResponseDto signUp(SignUpRequestDto request);
    UserResponseDto logIn(LogInRequestDto request);
    LogOutResponseDto logOut(LogOutRequestDto request);
    ValidateTokenResponseDto validateToken(ValidateTokenRequestDto request);
    Session createSession(String token, UUID userId);

}
