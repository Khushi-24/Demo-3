package com.example.Demo3.controller;

import com.example.Demo3.dtos.SocietyDto;
import com.example.Demo3.exception.NotFoundException;
import com.example.Demo3.service.SocietyService;
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
public class SocietyController {

    private final SocietyService societyService;
    private final MessageSource messageSource;

    @PostMapping("/addSociety")
    @PreAuthorize("hasAnyRole('Admin','Society Admin')")
    public ResponseEntity<?> addSociety(@RequestBody SocietyDto societyDto, Locale locale){
        SocietyDto dto = societyService.addSociety(societyDto, locale);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PostMapping("/getAllSociety/{pageNo}")
    @PreAuthorize("hasAnyRole('Admin','Society Admin')")
    public ResponseEntity<?> getAllSociety(@PathVariable int pageNo, Locale locale){
        Page<SocietyDto> societyDtoPage = societyService.getAllSociety(pageNo);
        List<SocietyDto> societyDtoList = societyDtoPage.getContent();
        if(pageNo> societyDtoPage.getTotalPages()){
            throw new NotFoundException(HttpStatus.NOT_FOUND, messageSource.getMessage("no_further_page_available.message", null, locale));
        }
        return new ResponseEntity<>(societyDtoList, HttpStatus.OK);
    }

    @GetMapping("/getSocietyBySocietyId/{societyId}")
    @PreAuthorize("hasAnyRole('Admin','Society Admin')")
    public ResponseEntity<?> getSocietyBySocietyId(@PathVariable Long societyId, Locale locale){
        SocietyDto societyDto = societyService.getSocietyBySocietyId(societyId, locale);
        return new ResponseEntity<>(societyDto, HttpStatus.OK);
    }

    @GetMapping("/getSocietyByAreaId/{areaId}")
    @PreAuthorize("hasAnyRole('Admin','Society Admin')")
    public ResponseEntity<?> getSocietyByAreaId(@PathVariable Long areaId, Locale locale){
        List<SocietyDto> societyDtoList = societyService.getSocietyByAreaId(areaId, locale);
        return new ResponseEntity<>(societyDtoList, HttpStatus.OK);
    }
}
