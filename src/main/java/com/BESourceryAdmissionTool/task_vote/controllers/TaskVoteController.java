package com.BESourceryAdmissionTool.task_vote.controllers;

import com.BESourceryAdmissionTool.task_vote.services.TaskVoteService;
import com.BESourceryAdmissionTool.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("vote")
public class TaskVoteController {

    private final TaskVoteService taskVoteService;

    @Autowired
    public TaskVoteController(TaskVoteService taskVoteService) {
        this.taskVoteService = taskVoteService;
    }

    @PostMapping("{taskId}")
    @ResponseStatus(code = HttpStatus.CREATED, reason = "Created")
    public void addVote(@RequestHeader(HttpHeaders.AUTHORIZATION) String authentication, @AuthenticationPrincipal User user, @PathVariable("taskId") Long taskId) {
        taskVoteService.addVote(user, taskId);
    }

    @DeleteMapping("{taskId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Deleted")
    public void deleteVote(@RequestHeader(HttpHeaders.AUTHORIZATION) String authentication, @AuthenticationPrincipal User user, @PathVariable("taskId") Long taskId) {
        taskVoteService.deleteVote(user, taskId);
    }
}
