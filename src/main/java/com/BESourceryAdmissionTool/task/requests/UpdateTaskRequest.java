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
    @Length(min = 5, max = 50, message = "Title must be between 5 and 50 characters")
    private String title;

    @NotBlank(message = "Title must not be null and must contain at least one non-whitespace character")
    @Length(min = 5, max = 50, message = "Title must be between 5 and 50 characters")
    private String description;

    @Nullable
    @Length(min = 20, message = "Summary must contain at least 20 non-whitespace characters")
    private String summary;

    private long categoryId;

    @NotEmpty(message = "There must be at least one answer")
    private List<@Valid @NotNull(message = "Answer must not be null") AnswerRequest> answers;
}

