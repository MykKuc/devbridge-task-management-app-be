package com.BESourceryAdmissionTool.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoUserWithSuchTokenException extends RuntimeException{
    public NoUserWithSuchTokenException(String message){super(message);}
}
