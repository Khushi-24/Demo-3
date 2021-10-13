package com.example.Demo3.controller;

import com.example.Demo3.dtos.FamilyDto;
import com.example.Demo3.service.FamilyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FamilyController {

    private final FamilyService familyService;

    @PostMapping("/addFamily")
    private ResponseEntity<?> addFamily(@RequestBody FamilyDto familyDto){
        FamilyDto dto = familyService.addFamily(familyDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
