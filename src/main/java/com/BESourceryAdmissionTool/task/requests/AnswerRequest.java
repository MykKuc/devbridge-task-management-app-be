package com.BESourceryAdmissionTool.task.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerRequest {
    private String text;
    private boolean isCorrect;
}
