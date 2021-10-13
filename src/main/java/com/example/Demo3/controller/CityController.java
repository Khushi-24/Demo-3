package com.example.Demo3.controller;

import com.example.Demo3.dtos.CityDto;
import com.example.Demo3.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @PostMapping("/addCity")
    public ResponseEntity<?> addCity(@RequestBody CityDto cityDto){
        CityDto dto = cityService.addCity(cityDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
