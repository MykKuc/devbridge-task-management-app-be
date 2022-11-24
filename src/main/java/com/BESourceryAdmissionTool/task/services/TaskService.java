package com.BESourceryAdmissionTool.task.services;

import com.BESourceryAdmissionTool.task.dto.*;
import com.BESourceryAdmissionTool.task.model.Task;
import com.BESourceryAdmissionTool.task.repositories.TaskRepository;
import com.BESourceryAdmissionTool.task.services.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
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

    public FullTaskDto getTaskData(long id) {
        Task task = taskRepository.findTaskById(id);
        List<AnswersDto> list = task.getAnswers().stream().map((answer) -> new AnswersDto(answer.getText(), answer.isCorrect())).collect(Collectors.toList());
        return new FullTaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getSummary(),
                task.getCreationDate(),
                task.getScore(),
                new UserDto(task.getAuthor().getId(), task.getAuthor().getName()),
                new CategoryDto(task.getCategory().getId(), task.getCategory().getName()),
                list
        );
    }

    public List<TaskDto> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(taskMapper::map)
                .collect(Collectors.toList());
    }
}
