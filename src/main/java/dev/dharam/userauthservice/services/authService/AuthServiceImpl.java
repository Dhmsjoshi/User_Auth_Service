package dev.dharam.userauthservice.services.authService;

import dev.dharam.userauthservice.Dtos.*;
import dev.dharam.userauthservice.dtoMapper.UserMapper;
import dev.dharam.userauthservice.entity.Role;
import dev.dharam.userauthservice.entity.Session;
import dev.dharam.userauthservice.entity.SessionStatus;
import dev.dharam.userauthservice.entity.User;
import dev.dharam.userauthservice.exceptions.*;
import dev.dharam.userauthservice.repositories.SessionRepository;
import dev.dharam.userauthservice.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class AuthServiceImpl implements AuthService{

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SessionRepository sessionRepository;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository,
                           SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.sessionRepository = sessionRepository;
    }

    @Override
    public UserResponseDto signUp(SignUpRequestDto request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        if(userOptional.isPresent()){
            throw new UserAlreadyExistsException("User already exist with email: "+request.getEmail());
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        User savedUser = userRepository.save(user);
        return UserMapper.convertUserToUserResponseDto(savedUser);
    }

    @Override
    public UserResponseDto logIn(LogInRequestDto request) {
        User user= userRepository.findByEmail(request.getEmail()).orElseThrow(
                ()-> new UserDoesNotExistException("User does not exist with email: "+request.getEmail())
        );

        if(!bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new UnAuthorizedException("Wrong password..!");
        }


        UserResponseDto responseDto = UserMapper.convertUserToUserResponseDto(user);
        return responseDto;
    }

    @Override
    public LogOutResponseDto logOut(LogOutRequestDto request) {
        Session session = sessionRepository.findByToken( request.getToken()).orElseThrow(
                ()->new InvalidTokenException("Invalid token!")
        );

        session.setSessionStatus(SessionStatus.LOGGED_OUT);
        sessionRepository.save(session);

        LogOutResponseDto logOutResponseDto = new LogOutResponseDto();
        logOutResponseDto.setSessionStatus(SessionStatus.LOGGED_OUT);
        return logOutResponseDto;
    }

    @Override
    public ValidateTokenResponseDto validateToken(ValidateTokenRequestDto request) {
        Session session = sessionRepository.findByToken( request.getToken()).orElseThrow(
                ()->new InvalidTokenException("Invalid token!")
        );

        if(session.getExpiringAt().compareTo(Instant.now()) <=0){
            session.setSessionStatus(SessionStatus.EXPIRED);
            session = sessionRepository.save(session);
        }

        if(session.getSessionStatus().equals(SessionStatus.EXPIRED)){
            throw new ExpiredTokenException("Session has been expired. Try LogIn again.!");
        }

        ValidateTokenResponseDto response = new ValidateTokenResponseDto();
        response.setSessionStatus(session.getSessionStatus());


        return response;
    }

    @Override
    public Session createSession(String token, UUID userId) {
        User user= userRepository.findById(userId).orElseThrow(
                ()-> new UserDoesNotExistException("User does not exist with id: "+userId)
        );

        Session session = new Session();
        session.setUser(user);
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setToken(token);
        session.setCreatedAt(Instant.now());
        session.setExpiringAt(Instant.now().plus(2, ChronoUnit.DAYS));
        return sessionRepository.save(session);

    }
}

