package com.example.Demo3.controller;

import com.example.Demo3.dtos.CompanyDto;
import com.example.Demo3.repository.CompanyRepository;
import com.example.Demo3.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/addCompany")
    private ResponseEntity<?> addCompany(@RequestBody CompanyDto companyDto){
        CompanyDto dto = companyService.addCompany(companyDto);
        return  new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
