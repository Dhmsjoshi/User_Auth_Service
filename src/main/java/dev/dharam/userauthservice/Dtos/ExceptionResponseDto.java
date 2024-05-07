package dev.dharam.userauthservice.Dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionResponseDto {
    private String message;
    private int code; // error code, http code etc.

    public ExceptionResponseDto(String message, int code) {
        this.message = message;
        this.code = code;
    }
}
