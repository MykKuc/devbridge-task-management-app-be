package com.BESourceryAdmissionTool.user.controller;

import com.BESourceryAdmissionTool.user.dto.AuthResponse;
import com.BESourceryAdmissionTool.user.dto.LoginDto;
import com.BESourceryAdmissionTool.user.model.User;
import com.BESourceryAdmissionTool.user.repositories.UserRepository;
import com.BESourceryAdmissionTool.user.security.JwtMaker;
import com.BESourceryAdmissionTool.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("users")
public class UserController {
    private final UserService userService;
    private AuthenticationManager authenticationManager;

    private JwtMaker jwtMaker;
    private UserRepository userRepository;


    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtMaker jwtMaker, UserRepository userRepository) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtMaker = jwtMaker;
        this.userRepository = userRepository;
    }


    @GetMapping("{id}")
    public Optional<User> getUser(@PathVariable("id") Long id) {
        return userService.getUser(id);
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponse> AuthenticateUser(@RequestBody LoginDto loginDto) throws Exception {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtMaker.generateToken(authentication);
        userService.storeJWt(loginDto.getEmail(), token);


        return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);

    }


}
