package com.BESourceryAdmissionTool.task.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class TaskDto {
    private long id;
    private String title;
    private String description;
    private String summary;
    private Date creationDate;
    private int score;
    private String authorId;
    private CategoryDto category;
}
