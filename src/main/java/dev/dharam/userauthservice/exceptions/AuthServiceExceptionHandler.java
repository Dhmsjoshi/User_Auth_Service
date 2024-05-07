package dev.dharam.userauthservice.exceptions;

import dev.dharam.userauthservice.Dtos.ExceptionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthServiceExceptionHandler {
    @ExceptionHandler(UserDoesNotExistException.class)
    public ResponseEntity handleNoProductException(UserDoesNotExistException userDoesNotExistException) {
        ExceptionResponseDto exceptionResponseDTO = new ExceptionResponseDto(
                userDoesNotExistException.getMessage(),
                404
        );
        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity handleNoProductException(UserAlreadyExistsException userAlreadyExistsException) {
        ExceptionResponseDto exceptionResponseDTO = new ExceptionResponseDto(
                userAlreadyExistsException.getMessage(),
                208
        );
        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.ALREADY_REPORTED);
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity handleNoProductException(UnAuthorizedException unAuthorizedException) {
        ExceptionResponseDto exceptionResponseDTO = new ExceptionResponseDto(
                unAuthorizedException.getMessage(),
                401
        );
        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity handleNoProductException(InvalidTokenException invalidTokenException) {
        ExceptionResponseDto exceptionResponseDTO = new ExceptionResponseDto(
                invalidTokenException.getMessage(),
                400
        );
        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity handleNoProductException(ExpiredTokenException expiredTokenException) {
        ExceptionResponseDto exceptionResponseDTO = new ExceptionResponseDto(
                expiredTokenException.getMessage(),
                403
        );
        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.FORBIDDEN);
    }



}
