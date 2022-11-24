package com.BESourceryAdmissionTool.task.controllers;

import com.BESourceryAdmissionTool.task.model.Task;
import com.BESourceryAdmissionTool.task.projections.CategoryDto;
import com.BESourceryAdmissionTool.task.projections.TaskDto;
import com.BESourceryAdmissionTool.task.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskDto> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return tasks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private TaskDto convertToDto(Task task) {
        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getSummary(),
                task.getCreationDate(),
                task.getScore(),
                task.getAuthorId(),
                new CategoryDto(task.getCategory().getId(), task.getCategory().getName())
        );
    }
}
