package com.BESourceryAdmissionTool.task.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnswersDto {
    private String text;
    private boolean isCorrect;
}
