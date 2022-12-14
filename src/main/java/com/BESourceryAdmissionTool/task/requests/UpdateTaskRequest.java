package com.BESourceryAdmissionTool.task.requests;

import com.BESourceryAdmissionTool.answer.model.Answer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UpdateTaskRequest {
    private long id;
    private String title;
    private String description;
    private String summary;
    private long categoryId;
    private List<AnswerRequest> answers;
}
