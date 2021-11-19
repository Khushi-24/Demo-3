package com.example.Demo3.controller;

import com.example.Demo3.dtos.FamilyDto;
import com.example.Demo3.exception.NotFoundException;
import com.example.Demo3.service.FamilyService;
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
public class FamilyController {

    private final FamilyService familyService;

    private final MessageSource messageSource;

    @PostMapping("/addFamily")
    @PreAuthorize("hasAnyRole('Admin','Society Admin')")
    public ResponseEntity<?> addFamily(@RequestBody FamilyDto familyDto, Locale locale){
        FamilyDto dto = familyService.addFamily(familyDto, locale);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/getFamilyByFamilyId/{familyId}")
    @PreAuthorize("hasAnyRole('Admin','Society Admin')")
    public ResponseEntity<?> getFamilyByFamilyId(@PathVariable Long familyId, Locale locale){
        FamilyDto familyDto = familyService.getFamilyByFamilyId(familyId, locale);
        return new ResponseEntity<>(familyDto, HttpStatus.OK);
    }

    @PostMapping("/getAllFamilies/{pageNo}")
    @PreAuthorize("hasAnyRole('Admin','Society Admin')")
    public ResponseEntity<?> getAllFamilies(@PathVariable int pageNo, Locale locale){
        Page<FamilyDto> familyDtoPage = familyService.getAllFamilies(pageNo, locale);
        List<FamilyDto> familyDtoList = familyDtoPage.getContent();
        if(pageNo> familyDtoPage.getTotalPages()){
            throw new NotFoundException(HttpStatus.NOT_FOUND, messageSource.getMessage("no_further_page_available.message", null, locale));
        }
        return new ResponseEntity<>(familyDtoList, HttpStatus.OK);
    }

    @GetMapping("/getAllFamiliesBySocietyId/{societyId}")
    @PreAuthorize("hasAnyRole('Admin','Society Admin')")
    public ResponseEntity<?> getAllFamiliesBySocietyId(@PathVariable Long societyId, Locale locale){
        List<FamilyDto> familyDtoList = familyService.getAllFamiliesBySocietyId(societyId, locale);
        return new ResponseEntity<>(familyDtoList, HttpStatus.OK);
    }
}
