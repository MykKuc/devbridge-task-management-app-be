package com.BESourceryAdmissionTool.task.services.mapper;

import com.BESourceryAdmissionTool.answer.model.Answer;
import com.BESourceryAdmissionTool.category.model.Category;
import com.BESourceryAdmissionTool.task.dto.*;
import com.BESourceryAdmissionTool.task.model.Task;
import com.BESourceryAdmissionTool.task.requests.AnswerRequest;
import com.BESourceryAdmissionTool.task.requests.TaskRequest;
import com.BESourceryAdmissionTool.task.requests.UpdateTaskRequest;
import com.BESourceryAdmissionTool.user.model.User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TaskMapper {
    public TaskDto taskMap(Task task){
        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getSummary(),
                task.getCreationDate(),
                task.getVotes().size(),
                task.getAuthor().getName(),
                new CategoryDto(task.getCategory().getId(), task.getCategory().getName())
        );
    }

    public Task taskMap(TaskRequest taskRequest, Category category, User author){
        return Task.builder()
                .id(null)
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .summary(taskRequest.getSummary())
                .creationDate(new Date())
                .category(category)
                .author(author)
                .answers(null)
                .build();
    }

    public FullTaskDto fullTaskMap(Task task) {
        return new FullTaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getSummary(),
                task.getCreationDate(),
                task.getVotes().size(),
                new UserDto(task.getAuthor().getId(), task.getAuthor().getName()),
                new CategoryDto(task.getCategory().getId(), task.getCategory().getName()),
                task.getAnswers()
        );
    }

    public Answer answerMap(String text, boolean isCorrect, Task task){
        return new Answer(null, text, isCorrect, task);
    }

}
