package com.BESourceryAdmissionTool.user.request;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NotNull
    @NotBlank
    @Length(min = 1, max = 50, message = "Incorrect Length of a name")
    private String name;

    @NotNull
    @NotBlank
    @Email(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$", message = "Incorrect provided email form.")
    @Length(min = 1, max = 50, message = "Incorrect Length of an email.")
    private String email;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^(?=.*[0-9]).{6,}")
    @Length(min = 1, max = 70, message = "Incorrect Length of a password.")
    @JsonIgnore
    private String password;
}
