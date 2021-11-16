package com.example.Demo3.controller;

import com.example.Demo3.dtos.CompanyDto;
import com.example.Demo3.exception.NotFoundException;
import com.example.Demo3.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    private final MessageSource messageSource;

    @PostMapping("/addCompany")
    @PreAuthorize("hasAnyRole('Admin','Company Admin')")
    public ResponseEntity<?> addCompany(@RequestBody CompanyDto companyDto, Locale locale){
        CompanyDto dto = companyService.addCompany(companyDto, locale);
        return  new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PostMapping("/getAllCompanies/{pageNo}")
    @PreAuthorize("hasAnyRole('Admin','Company Admin')")
    public ResponseEntity<?> getAllCompanies(@PathVariable int pageNo, Locale locale){
        Page<CompanyDto> companyDtoPage = companyService.getAllCompanies(pageNo, locale);
        List<CompanyDto> companyDtoList = companyDtoPage.getContent();
        if(pageNo> companyDtoPage.getTotalPages()){
            throw new NotFoundException(HttpStatus.NOT_FOUND, messageSource.getMessage("no_further_page_available.message", null, locale));
        }
        return new ResponseEntity<>(companyDtoList, HttpStatus.OK);
    }
}
