package com.example.Demo3.controller;

import com.example.Demo3.dtos.SocietyDto;
import com.example.Demo3.service.SocietyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SocietyController {

    private final SocietyService societyService;

    @PostMapping("/addSociety")
    public ResponseEntity<?> addSociety(@RequestBody SocietyDto societyDto){
        SocietyDto dto = societyService.addSociety(societyDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
