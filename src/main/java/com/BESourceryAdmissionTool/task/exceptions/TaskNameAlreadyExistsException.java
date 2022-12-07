package com.BESourceryAdmissionTool.task.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TaskNameAlreadyExistsException extends RuntimeException {
    public TaskNameAlreadyExistsException(String title) {
        super("Task already exists with title: " + title);
    }
}
