package com.BESourceryAdmissionTool.task.dto;

import com.BESourceryAdmissionTool.answer.model.Answer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UpdateTaskDto {
    private long id;
    private String title;
    private String description;
    private String summary;
    private CategoryDto category;
    private List<Answer> answers;
}
