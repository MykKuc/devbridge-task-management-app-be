package com.BESourceryAdmissionTool.task.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {

    @NotBlank(message = "Title must not be null and must contain at least one non-whitespace character")
    @Length(min = 1, max = 50, message = "Title must be between 1 and 50 characters")
    private String title;

    @NotBlank(message = "Description must not be null and must contain at least one non-whitespace character")
    @Length(min = 1, message = "Description must be at least 1 character")
    private String description;

    @Length(max = 100, message = "Summary cannot be over 100 characters")
    private String summary;

    // custom summary setter for validation
    public void setSummary(String summary) {
        this.summary = summary.trim();
    }

    private long categoryId;

    @NotEmpty(message = "There must be at least one answer")
    private List<@Valid @NotNull(message = "Answer must not be null") AnswerRequest> answers;
}
