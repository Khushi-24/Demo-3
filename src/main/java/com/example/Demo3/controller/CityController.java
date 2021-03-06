package com.example.Demo3.controller;

import com.example.Demo3.dtos.CityDto;
import com.example.Demo3.exception.NotFoundException;
import com.example.Demo3.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @PostMapping("/addCity")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> addCity(@RequestBody CityDto cityDto){
        CityDto dto = cityService.addCity(cityDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PostMapping("/getAllCities/{pageNo}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> getAllCities(@PathVariable int pageNo){
        Page<CityDto> cityDtoPage = cityService.getAllCities(pageNo);
        List<CityDto> cityDtoList = cityDtoPage.getContent();
        if(pageNo> cityDtoPage.getTotalPages()){
            throw new NotFoundException(HttpStatus.NOT_FOUND, "No further page available");
        }
        return new ResponseEntity<>(cityDtoList, HttpStatus.OK);
    }

    @GetMapping("/getCityById/{cityId}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> getCityById(@PathVariable Long cityId){
        CityDto cityDto = cityService.getCityById(cityId);
        return new ResponseEntity<>(cityDto, HttpStatus.OK);
    }

}
