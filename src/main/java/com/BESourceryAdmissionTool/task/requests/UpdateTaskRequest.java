package com.BESourceryAdmissionTool.task.requests;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class UpdateTaskRequest {
    private long id;

    @NotBlank(message = "Title must not be null and must contain at least one non-whitespace character")
    @Length(min = 1, max = 50, message = "Title must be between 1 and 50 characters")
    private String title;

    @NotBlank(message = "Description must not be null and must contain at least one non-whitespace character")
    @Length(min = 1, message = "Description must be at least 1 character")
    private String description;

    @Nullable
    @Length(min = 1, max = 100, message = "Summary must contain at least 1 non-whitespace character and cannot be over 100 characters")
    private String summary;

    private long categoryId;

    @NotEmpty(message = "There must be at least one answer")
    private List<@Valid @NotNull(message = "Answer must not be null") AnswerRequest> answers;
}

