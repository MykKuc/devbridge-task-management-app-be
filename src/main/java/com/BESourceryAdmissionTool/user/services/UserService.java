package com.BESourceryAdmissionTool.user.services;

import com.BESourceryAdmissionTool.task.exceptions.UserNotLoggedInException;
import com.BESourceryAdmissionTool.user.exceptions.UnauthorizedExeption;
import com.BESourceryAdmissionTool.user.exceptions.UserNotFoundException;
import com.BESourceryAdmissionTool.user.model.User;
import com.BESourceryAdmissionTool.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new UnauthorizedExeption("user is not existing");
        }
        User user = userOptional.get();
        user.setToken(token);
        userRepository.save(user);

    }

    public void deleteToken(User user) {
        if (user == null || user.getToken() == null) {
            throw new UnauthorizedExeption("You are not logged in");
        }
        user.setToken(null);
        userRepository.save(user);
    }

    public Optional<User> getUser(long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException(id);
        }
        return user;
    }

    public Optional<User> getCurrentUserByJwtToken(String currentJwtToken) {

        if(currentJwtToken == null){
            throw new UserNotLoggedInException("JWT token not provided");
        }

        Optional<User> currentUser = userRepository.findByToken(currentJwtToken);
        if(currentUser.isEmpty()){
            throw new UserNotLoggedInException("No User is currently logged in.");
        }

        return currentUser;
    }
}
