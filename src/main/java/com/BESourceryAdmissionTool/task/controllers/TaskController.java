package com.BESourceryAdmissionTool.task.controllers;

import com.BESourceryAdmissionTool.task.dto.FullTaskDto;
import com.BESourceryAdmissionTool.task.dto.TaskDto;
import com.BESourceryAdmissionTool.task.requests.UpdateTaskRequest;
import com.BESourceryAdmissionTool.task.requests.TaskRequest;
import com.BESourceryAdmissionTool.task.services.TaskService;
import com.BESourceryAdmissionTool.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("{id}")
    public Optional<FullTaskDto> getTaskData(@PathVariable("id") Long id, @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authentication, @AuthenticationPrincipal User user) {
        return taskService.getTaskData(id, user);
    }

    @GetMapping
    public List<TaskDto> getAllTasks(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authentication, @AuthenticationPrincipal User user) {
        return taskService.getAllTasks(user);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED, reason = "Created")
    public void createTask(@Valid @RequestBody TaskRequest taskRequest) {
        taskService.createTask(taskRequest);
    }

    @PutMapping(path ="{id}")
    public void updateTask(@RequestHeader(HttpHeaders.AUTHORIZATION) String authentication, @AuthenticationPrincipal User user,
                            @PathVariable("id") long id, @RequestBody UpdateTaskRequest request){
        taskService.updateTask(id, request, user);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Deleted")
    public void deleteTask(@RequestHeader(HttpHeaders.AUTHORIZATION) String authentication, @AuthenticationPrincipal User user,
                            @PathVariable("id") Long id) {
        taskService.deleteTask(id,user);
    }


}
