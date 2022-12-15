package com.BESourceryAdmissionTool.category.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CurrentUserIdNotEqualAuthorIdException extends RuntimeException{

    public CurrentUserIdNotEqualAuthorIdException(String message){super(message);}
}
