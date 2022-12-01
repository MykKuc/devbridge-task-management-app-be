package com.BESourceryAdmissionTool.task.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {
    private String title;
    private String description;
    private String summary;
    private long categoryId;
    private List<AnswerRequest> answers;
}
