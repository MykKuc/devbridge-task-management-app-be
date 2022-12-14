package com.BESourceryAdmissionTool.category.requests;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    @NotBlank(message = "Title must not be null and must contain at least one non-whitespace character")
    @Length(min = 1, max = 50, message = "Title must be between 1 and 50 characters")
    private String name;

    @NotBlank(message = "Description must not be null and must contain at least one non-whitespace character")
    @Length(min = 10, max = 200, message = "Description must be between 10 and 200 characters")
    private String description;
}
