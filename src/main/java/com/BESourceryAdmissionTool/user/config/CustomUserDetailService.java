package com.BESourceryAdmissionTool.user.config;


import com.BESourceryAdmissionTool.task.model.UserEntity;
import com.BESourceryAdmissionTool.user.repositories.UserRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private UserRepository userRepository;
@Autowired
    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
UserEntity user= userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("this email is not registered"));

        return  new User(user.getEmail(), user.getPassword(),  user.getAuthorities()) {
        };
    }
}
