package com.BESourceryAdmissionTool.user.services.mapper;

import com.BESourceryAdmissionTool.user.dto.UserMeDto;
import com.BESourceryAdmissionTool.user.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserMeDto mapUser(User user){
        return new UserMeDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getToken(),
                user.getRole()
        );
    }
}
