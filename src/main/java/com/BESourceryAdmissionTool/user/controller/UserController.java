package com.BESourceryAdmissionTool.user.controller;

import com.BESourceryAdmissionTool.user.dto.AuthResponse;
import com.BESourceryAdmissionTool.user.request.LoginRequest;
import com.BESourceryAdmissionTool.user.model.User;
import com.BESourceryAdmissionTool.user.repositories.UserRepository;
import com.BESourceryAdmissionTool.user.request.UserRequest;
import com.BESourceryAdmissionTool.user.security.JwtMaker;
import com.BESourceryAdmissionTool.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtMaker jwtMaker;


    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtMaker jwtMaker) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtMaker = jwtMaker;
    }


    @GetMapping("{id}")
    public Optional<User> getUser(@PathVariable("id") Long id) {
        return userService.getUser(id);
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody LoginRequest loginRequest) throws Exception {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtMaker.generateToken(authentication);
        userService.storeJWt(loginRequest.getEmail(), token);


        return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);

    }


    @PostMapping
    @ResponseStatus(code=HttpStatus.CREATED, reason = "created")
    public void addUser(@RequestBody UserRequest userRequest){
        userService.createUser(userRequest);
    }


    @PutMapping("logout")
    public ResponseEntity<String > deleteToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authentication,
                                               @AuthenticationPrincipal User user) throws Exception {
        userService.deleteToken(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
