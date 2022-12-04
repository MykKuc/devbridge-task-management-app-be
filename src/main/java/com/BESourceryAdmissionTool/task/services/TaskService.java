package com.BESourceryAdmissionTool.task.services;

import com.BESourceryAdmissionTool.task.dto.*;
import com.BESourceryAdmissionTool.task.exceptions.TaskNotFoundException;
import com.BESourceryAdmissionTool.task.model.Task;
import com.BESourceryAdmissionTool.task.repositories.TaskRepository;
import com.BESourceryAdmissionTool.task.services.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public Optional<FullTaskDto> getTaskData(long id) {
        Optional<Task> task = taskRepository.findTaskById(id);
        if (task.isEmpty()){
            throw new TaskNotFoundException("Task not found");
        }
        return task.map(taskMapper::fullTaskMap);
    }

    public List<TaskDto> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(taskMapper::taskMap)
                .collect(Collectors.toList());
    }

    public void updateTask(long id, Task task){
        taskRepository.findTaskById(id);
    }
}
