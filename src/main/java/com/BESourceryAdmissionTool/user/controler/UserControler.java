package com.BESourceryAdmissionTool.user.controler;


import com.BESourceryAdmissionTool.user.dto.LoginDto;
import com.BESourceryAdmissionTool.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("user")
public class UserControler {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;

    @Autowired
    public UserControler(AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }


    @PostMapping("login")
    public ResponseEntity<String> AuthenticateUser(@RequestBody LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);


        return new ResponseEntity<>("Log in ",HttpStatus.OK);


    }
}
