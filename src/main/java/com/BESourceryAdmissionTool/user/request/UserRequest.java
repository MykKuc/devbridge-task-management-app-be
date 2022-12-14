package com.BESourceryAdmissionTool.user.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NotBlank(message = "Name must not be null or blank")
    @Length(min = 1, max = 50, message = "Incorrect Length of a name")
    private String name;

    @NotBlank(message = "Email must not be null or blank")
    @Email(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$", message = "Incorrect provided email form.")
    @Length(min = 1, max = 50, message = "Incorrect Length of an email.")
    private String email;

    @NotBlank(message = "Password must not be null or blank")
    @Pattern(regexp = "^(?=.*[0-9]).{6,}", message = "Password must be at least 6 characters and have 1 number.")
    @Length(min = 1, max = 70, message = "Incorrect Length of a password.")
    private String password;
}
