package com.BESourceryAdmissionTool.user.services;

import com.BESourceryAdmissionTool.user.exceptions.UserAlreadyExistsException;
import com.BESourceryAdmissionTool.user.exceptions.UserNotFoundException;
import com.BESourceryAdmissionTool.user.model.User;
import com.BESourceryAdmissionTool.user.repositories.UserRepository;
import com.BESourceryAdmissionTool.user.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void storeJWt(String email, String token) throws Exception {
       Optional<User> userOptional =userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new Exception();
        }
        User user=userOptional.get();
        user.setToken(token);
        userRepository.save(user);

    }

    public Optional<User> getUser(long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new UserNotFoundException(id);
        }
        return user;
    }

    public void createUser(UserRequest userRequest) {

        Optional<User> sameUser = userRepository.findByEmail(userRequest.getEmail());
        if(sameUser.isPresent()){
            throw new UserAlreadyExistsException(userRequest.getEmail());
        }

        User user = User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .build();


        userRepository.save(user);

    }
}
