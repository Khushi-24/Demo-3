package com.example.Demo3.dtos;

import com.example.Demo3.entities.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {

    private User user;
    private String jwtToken;

    public JwtResponse(User user, String jwtToken) {
        this.user = user;
        this.jwtToken = jwtToken;
    }
}
