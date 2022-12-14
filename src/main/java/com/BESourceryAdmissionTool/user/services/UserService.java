package com.BESourceryAdmissionTool.user.services;

import com.BESourceryAdmissionTool.user.exceptions.UnauthorizedExeption;
import com.BESourceryAdmissionTool.user.exceptions.UserNotFoundException;
import com.BESourceryAdmissionTool.user.model.User;
import com.BESourceryAdmissionTool.user.repositories.UserRepository;
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
    public void checkUser(User user, String token){
        if (user==null|| user.getToken() == null){
            throw new UnauthorizedExeption("user dont exist");
        }else if (token.equals(user.getToken())){
            throw new UnauthorizedExeption("Wrong user");
        }
    }

    public Optional<User> getUser(long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException(id);
        }
        return user;
    }
}
