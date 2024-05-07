package dev.dharam.userauthservice.controllers;

import dev.dharam.userauthservice.Dtos.*;
import dev.dharam.userauthservice.entity.Session;
import dev.dharam.userauthservice.services.authService.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody SignUpRequestDto request){
        UserResponseDto response = authService.signUp(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> logIn(@RequestBody LogInRequestDto request){
        UserResponseDto savedUser = authService.logIn(request);
        String token = RandomStringUtils.randomAscii(20);
        //TODO:Update here to use JWT
        /*Paylod{
                userId:
                email:
                roles:
            }
         */


        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("userId",savedUser.getId() );
        claimsMap.put("email", savedUser.getEmail());
        claimsMap.put("roles", savedUser.getRoles());
        String jwt = Jwts.builder()
                .claims(claimsMap)
                .compact();
        Session savedSession = authService.createSession(token, savedUser.getId());

        MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add("AUTH_TOKEN", jwt);



        ResponseEntity<UserResponseDto> response= new ResponseEntity<>(
                savedUser,
                headers,
                HttpStatus.OK
        );

        return response;

    }

    @PostMapping("/logout")
    public ResponseEntity<LogOutResponseDto> logOut(@RequestBody LogOutRequestDto request){
        LogOutResponseDto response = authService.logOut(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/validate")
    public ResponseEntity<ValidateTokenResponseDto> validateToken(@RequestBody ValidateTokenRequestDto request){
        ValidateTokenResponseDto response = authService.validateToken(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
