package com.BESourceryAdmissionTool.user.controler;


import com.BESourceryAdmissionTool.task.model.User;
import com.BESourceryAdmissionTool.user.dto.AuthResponse;
import com.BESourceryAdmissionTool.user.dto.LoginDto;
import com.BESourceryAdmissionTool.user.repositories.UserRepository;
import com.BESourceryAdmissionTool.user.security.JwtMaker;
import com.BESourceryAdmissionTool.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("user")

public class UserController {

    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JwtMaker jwtMaker;
    private  UserRepository userRepository;

    @Autowired
    public UserController(AuthenticationManager authenticationManager, UserService userService, UserRepository userRepository, JwtMaker jwtMaker) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.userRepository = userRepository;
        this.jwtMaker = jwtMaker;
    }


    @PostMapping("login")
    public ResponseEntity<AuthResponse> AuthenticateUser(@RequestBody LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtMaker.generateToken(authentication);


        return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);

    }

   @PutMapping("logout")
  public String deleteJwt(@AuthenticationPrincipal User user){
       


       return "ok";




}}