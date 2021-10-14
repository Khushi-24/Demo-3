package com.example.Demo3.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    @NotNull(message = "User email is required.")
    @NotBlank(message = "User email is required.")
    @Email(message = "Please enter valid syntax of email.")
    private String userEmail;

    @NotNull(message = "User name is required.")
    @NotBlank(message = "User name is required.")
    private String userName;

    @NotNull(message = "User password is required.")
    @NotBlank(message = "User password is required.")
    private String userPassword;

    @NotNull(message = "User role is required.")
    @NotBlank(message = "User role is required.")
    private String userRole;
}
