package com.BESourceryAdmissionTool.task_vote.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class TaskVoteFoundException extends RuntimeException {
    public TaskVoteFoundException(String message) {
        super(message);
    }
}
