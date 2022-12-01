package com.BESourceryAdmissionTool.task.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.BESourceryAdmissionTool.answer.model.Answer;

import java.util.List;
import java.util.Date;

@Data
@AllArgsConstructor
public class FullTaskDto {
    private long id;
    private String title;
    private String description;
    private String summary;
    private Date creationDate;
    private int score;
    private UserDto user;
    private CategoryDto category;
    private List<Answer> answers;
}
