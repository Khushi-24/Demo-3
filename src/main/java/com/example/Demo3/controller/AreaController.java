package com.example.Demo3.controller;

import com.example.Demo3.dtos.AreaDto;
import com.example.Demo3.dtos.CityDto;
import com.example.Demo3.exception.NotFoundException;
import com.example.Demo3.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AreaController {

    private final AreaService areaService;

    @PostMapping("/addArea")
    public ResponseEntity<?> addArea(@RequestBody AreaDto areaDto){
        AreaDto dto = areaService.addArea(areaDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/getAllArea/{pageNo}")
    public ResponseEntity<?> getAllArea(@PathVariable int pageNo){
        Page<AreaDto> areaDtoPage = areaService.getAllArea(pageNo);
        List<AreaDto> areaDtoList = areaDtoPage.getContent();
        if(pageNo> areaDtoPage.getTotalPages()){
            throw new NotFoundException(HttpStatus.NOT_FOUND, "No further page available");
        }
        return new ResponseEntity<>(areaDtoList, HttpStatus.OK);
    }
}
