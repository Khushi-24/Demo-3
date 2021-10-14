package com.example.Demo3.controller;

import com.example.Demo3.entities.User;
import com.example.Demo3.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/addUser")
    public User addUser(@RequestBody User user){
        user.setUserPassword(getEncodedPassword(user.getUserPassword()));
        userRepository.save(user);
        return user;
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}

