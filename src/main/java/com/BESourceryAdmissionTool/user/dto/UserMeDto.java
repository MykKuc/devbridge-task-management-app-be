package com.BESourceryAdmissionTool.user.dto;

import com.BESourceryAdmissionTool.user.role.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserMeDto {
    private long id;
    private String name;
    private String email;
    private String token;
    private Role role;
}
