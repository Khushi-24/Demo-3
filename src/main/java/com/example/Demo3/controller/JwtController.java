package com.example.Demo3.controller;

import com.example.Demo3.dtos.JwtRequest;
import com.example.Demo3.dtos.JwtResponse;
import com.example.Demo3.serviceImpl.JwtServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class JwtController {

    private final JwtServiceImpl jwtService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception{

        JwtResponse jwtResponse = jwtService.createJwtToken(jwtRequest);
        return new ResponseEntity<>(jwtResponse, HttpStatus.ACCEPTED);

    }
}
