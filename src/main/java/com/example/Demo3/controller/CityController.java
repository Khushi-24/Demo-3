package com.example.Demo3.controller;

import com.example.Demo3.dtos.CityDto;
import com.example.Demo3.exception.NotFoundException;
import com.example.Demo3.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;
    private final MessageSource messageSource;

    @PostMapping("/addCity")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> addCity(@RequestBody CityDto cityDto, Locale locale){
        CityDto dto = cityService.addCity(cityDto, locale);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PostMapping("/getAllCities/{pageNo}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> getAllCities(@PathVariable int pageNo, Locale locale){
        Page<CityDto> cityDtoPage = cityService.getAllCities(pageNo);
        List<CityDto> cityDtoList = cityDtoPage.getContent();
        if(pageNo> cityDtoPage.getTotalPages()){
            throw new NotFoundException(HttpStatus.NOT_FOUND, messageSource.getMessage("no_further_page_available.message", null, locale));
        }
        return new ResponseEntity<>(cityDtoList, HttpStatus.OK);
    }

    @GetMapping("/getCityById/{cityId}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> getCityById(@PathVariable Long cityId, Locale locale){
        CityDto cityDto = cityService.getCityById(cityId, locale);
        return new ResponseEntity<>(cityDto, HttpStatus.OK);
    }

}
