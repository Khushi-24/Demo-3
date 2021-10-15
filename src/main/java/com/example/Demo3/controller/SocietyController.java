package com.example.Demo3.controller;

import com.example.Demo3.dtos.AreaDto;
import com.example.Demo3.dtos.SocietyDto;
import com.example.Demo3.entities.Society;
import com.example.Demo3.exception.NotFoundException;
import com.example.Demo3.repository.SocietyRepository;
import com.example.Demo3.service.SocietyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SocietyController {

    private final SocietyService societyService;

    @PostMapping("/addSociety")
    @PreAuthorize("hasAnyRole('Admin','Society Admin')")
    public ResponseEntity<?> addSociety(@RequestBody SocietyDto societyDto){
        SocietyDto dto = societyService.addSociety(societyDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PostMapping("/getAllSociety/{pageNo}")
    @PreAuthorize("hasAnyRole('Admin','Society Admin')")
    public ResponseEntity<?> getAllSociety(@PathVariable int pageNo){
        Page<SocietyDto> societyDtoPage = societyService.getAllSociety(pageNo);
        List<SocietyDto> societyDtoList = societyDtoPage.getContent();
        if(pageNo> societyDtoPage.getTotalPages()){
            throw new NotFoundException(HttpStatus.NOT_FOUND, "No further page available");
        }
        return new ResponseEntity<>(societyDtoList, HttpStatus.OK);
    }

    @GetMapping("/getSocietyBySocietyId/{societyId}")
    @PreAuthorize("hasAnyRole('Admin','Society Admin')")
    public ResponseEntity<?> getSocietyBySocietyId(@PathVariable Long societyId){
        SocietyDto societyDto = societyService.getSocietyBySocietyId(societyId);
        return new ResponseEntity<>(societyDto, HttpStatus.OK);
    }

    @GetMapping("/getSocietyByAreaId/{areaId}")
    @PreAuthorize("hasAnyRole('Admin','Society Admin')")
    public ResponseEntity<?> getSocietyByAreaId(@PathVariable Long areaId){
        List<SocietyDto> societyDtoList = societyService.getSocietyByAreaId(areaId);
        return new ResponseEntity<>(societyDtoList, HttpStatus.OK);
    }
}
