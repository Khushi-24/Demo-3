package com.example.Demo3.controller;

import com.example.Demo3.dtos.CompanyEmployeeDto;
import com.example.Demo3.service.CompanyEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CompanyEmployeeController {

    private final CompanyEmployeeService companyEmployeeService;

    @PostMapping("/addMemberToCompany")
    private ResponseEntity<?> addMemberToCompany(@RequestBody CompanyEmployeeDto companyEmployeeDto){
        CompanyEmployeeDto dto = companyEmployeeService.addMemberToCompany(companyEmployeeDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
