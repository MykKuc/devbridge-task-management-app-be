package com.BESourceryAdmissionTool.task.controllers;

import com.BESourceryAdmissionTool.task.dto.FullTaskDto;
import com.BESourceryAdmissionTool.task.dto.TaskDto;
import com.BESourceryAdmissionTool.task.requests.UpdateTaskRequest;
import com.BESourceryAdmissionTool.task.requests.TaskRequest;
import com.BESourceryAdmissionTool.task.services.TaskService;
import com.BESourceryAdmissionTool.user.exceptions.UnauthorizedExeption;
import com.BESourceryAdmissionTool.user.model.User;
import com.BESourceryAdmissionTool.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
    private final UserService userService;

    @Autowired
    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("{id}")
    public Optional<FullTaskDto> getTaskData(@PathVariable("id") Long id) {
        return taskService.getTaskData(id);
    }

    @GetMapping
    public List<TaskDto> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED, reason = "Created")
    public void createTask(@Valid @RequestBody TaskRequest taskRequest,  @RequestHeader(HttpHeaders.AUTHORIZATION) String authentication,
                           @AuthenticationPrincipal User user) throws UnauthorizedExeption {
        userService.checkUser(user,authentication);
        taskService.createTask(taskRequest);
    }

    @PutMapping(path ="{id}")
    public void updateTask(@PathVariable("id") long id, @RequestBody UpdateTaskRequest request,  @RequestHeader(HttpHeaders.AUTHORIZATION) String authentication,
                           @AuthenticationPrincipal User user) throws UnauthorizedExeption {
        userService.checkUser(user,authentication);
        taskService.updateTask(id, request);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Deleted")
    public void deleteTask(@PathVariable("id") Long id, @RequestHeader(HttpHeaders.AUTHORIZATION) String authentication,
                           @AuthenticationPrincipal User user) throws UnauthorizedExeption {
        userService.checkUser(user,authentication); taskService.deleteTask(id);}

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
