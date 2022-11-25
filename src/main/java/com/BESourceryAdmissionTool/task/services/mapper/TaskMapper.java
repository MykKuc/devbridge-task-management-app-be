package com.BESourceryAdmissionTool.task.services.mapper;

import com.BESourceryAdmissionTool.task.dto.FullTaskDto;
import com.BESourceryAdmissionTool.task.model.Task;
import com.BESourceryAdmissionTool.task.dto.CategoryDto;
import com.BESourceryAdmissionTool.task.dto.TaskDto;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public TaskDto map(Task task){
        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getSummary(),
                task.getCreationDate(),
                task.getScore(),
                task.getAuthor().getId(),
                new CategoryDto(task.getCategory().getId(), task.getCategory().getName())
        );
    }
}
