package com.example.Demo3.controller;

import com.example.Demo3.dtos.CityDto;
import com.example.Demo3.dtos.FamilyDto;
import com.example.Demo3.exception.NotFoundException;
import com.example.Demo3.service.FamilyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FamilyController {

    private final FamilyService familyService;

    @PostMapping("/addFamily")
    @PreAuthorize("hasAnyRole('Admin','Society Admin')")
    private ResponseEntity<?> addFamily(@RequestBody FamilyDto familyDto){
        FamilyDto dto = familyService.addFamily(familyDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/getFamilyByFamilyId/{familyId}")
    @PreAuthorize("hasAnyRole('Admin','Society Admin')")
    private ResponseEntity<?> getFamilyByFamilyId(@PathVariable Long familyId){
        FamilyDto familyDto = familyService.getFamilyByFamilyId(familyId);
        return new ResponseEntity<>(familyDto, HttpStatus.OK);
    }

    @PostMapping("/getAllFamilies/{pageNo}")
    @PreAuthorize("hasAnyRole('Admin','Society Admin')")
    private ResponseEntity<?> getAllFamilies(@PathVariable int pageNo){
        Page<FamilyDto> familyDtoPage = familyService.getAllFamilies(pageNo);
        List<FamilyDto> familyDtoList = familyDtoPage.getContent();
        if(pageNo> familyDtoPage.getTotalPages()){
            throw new NotFoundException(HttpStatus.NOT_FOUND, "No further page available");
        }
        return new ResponseEntity<>(familyDtoList, HttpStatus.OK);
    }

    @GetMapping("/getAllFamiliesBySocietyId/{societyId}")
    @PreAuthorize("hasAnyRole('Admin','Society Admin')")
    private ResponseEntity<?> getAllFamiliesBySocietyId(@PathVariable Long societyId){
        List<FamilyDto> familyDtoList = familyService.getAllFamiliesBySocietyId(societyId);
        return new ResponseEntity<>(familyDtoList, HttpStatus.OK);
    }
}
