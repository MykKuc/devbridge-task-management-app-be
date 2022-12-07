package com.BESourceryAdmissionTool.task.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerRequest {

    @NotBlank(message = "Answer text must not be null and must contain at least one non-whitespace character")
    private String text;

    private boolean isCorrect;
}
