package dev.dharam.userauthservice.controllers;

import dev.dharam.userauthservice.Dtos.SetUserRolesRequestDto;
import dev.dharam.userauthservice.Dtos.UserDetailsResponseDto;
import dev.dharam.userauthservice.Dtos.UserResponseDto;
import dev.dharam.userauthservice.services.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsResponseDto> getUserDetails(@PathVariable("id")UUID userId){
        UserDetailsResponseDto response = userService.getUserDetails(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{id}/roles")
    public ResponseEntity<UserDetailsResponseDto> setUserRoles(@PathVariable("id") UUID userID, @RequestBody SetUserRolesRequestDto rolesRequest){
        UserDetailsResponseDto response = userService.setUserRoles(userID, rolesRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
