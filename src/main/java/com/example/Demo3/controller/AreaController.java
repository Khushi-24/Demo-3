package com.example.Demo3.controller;

import com.example.Demo3.dtos.AreaDto;
import com.example.Demo3.exception.NotFoundException;
import com.example.Demo3.service.AreaService;
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
public class AreaController {

    private final AreaService areaService;

    private final MessageSource messageSource;


    @PostMapping("/addArea")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> addArea(@RequestBody AreaDto areaDto, Locale locale){
        AreaDto dto = areaService.addArea(areaDto, locale);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PostMapping("/getAllArea/{pageNo}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> getAllArea(@PathVariable int pageNo, Locale locale){
        Page<AreaDto> areaDtoPage = areaService.getAllArea(pageNo);
        List<AreaDto> areaDtoList = areaDtoPage.getContent();
        if(pageNo> areaDtoPage.getTotalPages()){
            String message = messageSource.getMessage("no_further_page_available.message", null, locale);
            throw new NotFoundException(HttpStatus.NOT_FOUND, message);
        }
        return new ResponseEntity<>(areaDtoList, HttpStatus.OK);
    }

    @GetMapping("/getAreaByAreaId/{areaId}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> getAreaByAreaId(@PathVariable Long areaId, Locale locale){
        AreaDto areaDto = areaService.getAreaByAreaId(areaId, locale);
        return new ResponseEntity<>(areaDto, HttpStatus.OK);
    }

    @GetMapping("/getListOfAreaByCityId/{cityId}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> getListOfAreaByCityId(@PathVariable Long cityId, Locale locale){
        List<AreaDto> areaDtoList = areaService.getListOfAreaByCityId(cityId, locale);
        return new ResponseEntity<>(areaDtoList, HttpStatus.OK);
    }
}
