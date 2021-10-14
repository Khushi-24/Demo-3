package com.example.Demo3.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class JwtRequest {

    @NotBlank(message = "UserEmail can,t be null.")
    private String userEmail;

    @NotBlank(message = "Password can't be null.")
    private String userPassword;
}
