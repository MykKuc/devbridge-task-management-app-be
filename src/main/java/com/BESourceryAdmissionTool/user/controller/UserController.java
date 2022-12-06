package com.BESourceryAdmissionTool.user.controller;

import com.BESourceryAdmissionTool.user.model.User;
import com.BESourceryAdmissionTool.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    public Optional<User> getUser(@PathVariable("id") Long id){
        return userService.getUser(id);
    }
}
