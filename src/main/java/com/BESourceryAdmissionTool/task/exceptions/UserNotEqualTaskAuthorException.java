package com.BESourceryAdmissionTool.task.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserNotEqualTaskAuthorException extends RuntimeException{
    public UserNotEqualTaskAuthorException(String message){super (message);}
}
