package com.BESourceryAdmissionTool.task_vote.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TaskVoteNotFoundException extends RuntimeException {
    public TaskVoteNotFoundException(String message) {
        super(message);
    }
}
