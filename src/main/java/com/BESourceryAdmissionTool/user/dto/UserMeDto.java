package com.BESourceryAdmissionTool.user.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserMeDto {
    private long id;
    private String name;
    private String email;
    private String token;
}
