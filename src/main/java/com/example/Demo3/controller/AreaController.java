package com.example.Demo3.controller;

import com.example.Demo3.dtos.AreaDto;
import com.example.Demo3.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AreaController {

    private final AreaService areaService;

    @PostMapping("/addArea")
    public ResponseEntity<?> addArea(@RequestBody AreaDto areaDto){
        AreaDto dto = areaService.addArea(areaDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
