package dev.dharam.userauthservice.services.authService;

import dev.dharam.userauthservice.Dtos.*;
import dev.dharam.userauthservice.dtoMapper.UserMapper;
import dev.dharam.userauthservice.entity.Role;
import dev.dharam.userauthservice.entity.Session;
import dev.dharam.userauthservice.entity.SessionStatus;
import dev.dharam.userauthservice.entity.User;
import dev.dharam.userauthservice.exceptions.*;
import dev.dharam.userauthservice.repositories.RoleRepository;
import dev.dharam.userauthservice.repositories.SessionRepository;
import dev.dharam.userauthservice.repositories.UserRepository;
import io.jsonwebtoken.security.Jwks;
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
    private RoleRepository roleRepository;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository,
                           SessionRepository sessionRepository,
                           RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.roleRepository = roleRepository;
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
        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName("USER").orElseThrow(
                ()->new RuntimeException("Unknown error")
        );

        roles.add(role);
        user.setRoles(roles);
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
    public Optional<ValidateTokenResponseDto> validateToken(ValidateTokenRequestDto request) {
        Optional<Session> sessionOptional = sessionRepository.findByToken( request.getToken());
        if(sessionOptional.isEmpty()){
            Optional.empty();
        }
        Session session = sessionOptional.get();

        if (!session.getSessionStatus().equals(SessionStatus.ACTIVE)) {
            return Optional.empty();
        }
//        if(session.getExpiringAt().compareTo(Instant.now()) <=0){
//            session.setSessionStatus(SessionStatus.EXPIRED);
//            session = sessionRepository.save(session);
//        }
//
//        if(session.getSessionStatus().equals(SessionStatus.EXPIRED)){
//            throw new ExpiredTokenException("Session has been expired. Try LogIn again.!");
//        }

        User user = userRepository.findById(request.getUserId()).get();
        UserResponseDto userResponseDto = UserMapper.convertUserToUserResponseDto(user);


        ValidateTokenResponseDto response = new ValidateTokenResponseDto();
        response.setSessionStatus(session.getSessionStatus());
        response.setUserResponseDto(userResponseDto);
        return Optional.of(response);
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

