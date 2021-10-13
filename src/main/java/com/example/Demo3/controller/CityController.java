package com.example.Demo3.controller;

import com.example.Demo3.dtos.CityDto;
import com.example.Demo3.exception.NotFoundException;
import com.example.Demo3.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @PostMapping("/addCity")
    public ResponseEntity<?> addCity(@RequestBody CityDto cityDto){
        CityDto dto = cityService.addCity(cityDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/getAllCities/{pageNo}")
    public ResponseEntity<?> getAllCities(@PathVariable int pageNo){
        Page<CityDto> cityDtoPage = cityService.getAllCities(pageNo);
        List<CityDto> cityDtoList = cityDtoPage.getContent();
        if(pageNo> cityDtoPage.getTotalPages()){
            throw new NotFoundException(HttpStatus.NOT_FOUND, "No further page available, total no. of available pages is " +(cityDtoPage.getTotalPages() + 1));
        }
        return new ResponseEntity<>(cityDtoList, HttpStatus.OK);
    }

}
