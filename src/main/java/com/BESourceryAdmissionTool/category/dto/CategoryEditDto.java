package com.BESourceryAdmissionTool.category.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryEditDto {
    private long id;
    private String name;
    private String description;
}
