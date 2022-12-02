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

    @NotBlank(message = "Title must not be null and must contain at least 5 non-whitespace characters")
    @Length(min = 5, max = 50, message = "Title must be between 5 and 50 characters")
    private String title;

    @NotBlank(message = "Description must not be null and must contain at least 40 non-whitespace characters")
    @Length(min = 40, message = "Description must be at least 40 characters")
    private String description;

    @Nullable
    @Length(min = 20, message = "Summary must contain at least 20 non-whitespace characters")
    private String summary;

    // custom summary setter for validation
    public void setSummary(String summary){
        if(summary != null){
            this.summary = summary.trim();
        }
    }

    private long categoryId;

    @NotEmpty(message = "There must be at least one answer")
    private List<@Valid @NotNull(message = "Answer must not be null") AnswerRequest> answers;
}
