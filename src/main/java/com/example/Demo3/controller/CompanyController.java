package com.example.Demo3.controller;

import com.example.Demo3.dtos.CityDto;
import com.example.Demo3.dtos.CompanyDto;
import com.example.Demo3.exception.NotFoundException;
import com.example.Demo3.repository.CompanyRepository;
import com.example.Demo3.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/addCompany")
    @PreAuthorize("hasAnyRole('Admin','Company Admin')")
    private ResponseEntity<?> addCompany(@RequestBody CompanyDto companyDto){
        CompanyDto dto = companyService.addCompany(companyDto);
        return  new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PostMapping("/getAllCompanies/{pageNo}")
    @PreAuthorize("hasAnyRole('Admin','Company Admin')")
    public ResponseEntity<?> getAllCompanies(@PathVariable int pageNo){
        Page<CompanyDto> companyDtoPage = companyService.getAllCompanies(pageNo);
        List<CompanyDto> companyDtoList = companyDtoPage.getContent();
        if(pageNo> companyDtoPage.getTotalPages()){
            throw new NotFoundException(HttpStatus.NOT_FOUND, "No further page available");
        }
        return new ResponseEntity<>(companyDtoList, HttpStatus.OK);
    }
}
