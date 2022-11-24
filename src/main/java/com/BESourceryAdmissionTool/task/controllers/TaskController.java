package com.BESourceryAdmissionTool.task.controllers;

import com.BESourceryAdmissionTool.task.model.Task;
import com.BESourceryAdmissionTool.task.projection.TaskData;
import com.BESourceryAdmissionTool.task.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tasks/")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public TaskData GetTaskData(@PathVariable("id") Long id) {
        System.out.println(taskService.getTaskData(id));
        return taskService.getTaskData(id);
    }
}
