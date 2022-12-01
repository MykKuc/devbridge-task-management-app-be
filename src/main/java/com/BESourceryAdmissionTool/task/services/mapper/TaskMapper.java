package com.BESourceryAdmissionTool.task.services.mapper;

import com.BESourceryAdmissionTool.answer.model.Answer;
import com.BESourceryAdmissionTool.task.dto.FullTaskDto;
import com.BESourceryAdmissionTool.task.dto.UserDto;
import com.BESourceryAdmissionTool.task.model.Task;
import com.BESourceryAdmissionTool.task.dto.CategoryDto;
import com.BESourceryAdmissionTool.task.dto.TaskDto;
import com.BESourceryAdmissionTool.task.requests.AnswerRequest;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public TaskDto taskMap(Task task){
        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getSummary(),
                task.getCreationDate(),
                task.getScore(),
                task.getAuthor().getName(),
                new CategoryDto(task.getCategory().getId(), task.getCategory().getName())
        );
    }

    public FullTaskDto fullTaskMap(Task task) {
        return new FullTaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getSummary(),
                task.getCreationDate(),
                task.getScore(),
                new UserDto(task.getAuthor().getId(), task.getAuthor().getName()),
                new CategoryDto(task.getCategory().getId(), task.getCategory().getName()),
                task.getAnswers()
        );
    }

    public Answer answerMap(AnswerRequest answerRequest, long taskId){
        return new Answer(
                null,
                answerRequest.getText(),
                answerRequest.isCorrect(),
                taskId
        );
    }

}
