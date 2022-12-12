package com.BESourceryAdmissionTool.user.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedExeption extends  RuntimeException{
    public UnauthorizedExeption(String message) {
        super (message);
    }
}
