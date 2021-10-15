package com.example.Demo3.controller;

import com.example.Demo3.dtos.*;
import com.example.Demo3.entities.CompanyEmployee;
import com.example.Demo3.exception.NotFoundException;
import com.example.Demo3.service.CompanyEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CompanyEmployeeController {

    private final CompanyEmployeeService companyEmployeeService;

    @PostMapping("/addMemberToCompany")
    @PreAuthorize("hasAnyRole('Admin','Company Admin')")
    private ResponseEntity<?> addMemberToCompany(@RequestBody CompanyEmployeeDto companyEmployeeDto){
        CompanyEmployeeDto dto = companyEmployeeService.addMemberToCompany(companyEmployeeDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/getListOfEmployeesHavingSalaryLessByAreaId")
    @PreAuthorize("hasAnyRole('Admin','Company Admin')")
    private ResponseEntity<?> getListOfEmployeesHavingSalaryLessByAreaId(@RequestBody RequestDtoForGettingEmployeesByAreaId dto){
        List<CompanyEmployeeDto> companyEmployeeDto = companyEmployeeService.getListOfEmployeesHavingSalaryLessByAreaId(dto);
        return new ResponseEntity<>(companyEmployeeDto, HttpStatus.OK);
    }

    @GetMapping("/getListOfEmployeesHavingSalaryLessThanAndByCityId")
    @PreAuthorize("hasAnyRole('Admin','Company Admin')")
    private ResponseEntity<?> getListOfEmployeesHavingSalaryLessThanAndByCityId(@RequestBody RequestDtoForGettingEmployeesByCityId dto){
        List<CompanyEmployeeDto> companyEmployeeDto = companyEmployeeService.getListOfEmployeesHavingSalaryLessThanAndByCityId(dto);
        return new ResponseEntity<>(companyEmployeeDto, HttpStatus.OK);
    }

    @GetMapping("/getListOfEmployeesHavingSalaryLessThanAndBySocietyId")
    @PreAuthorize("hasAnyRole('Admin','Company Admin')")
    private ResponseEntity<?> getListOfEmployeesHavingSalaryLessThanAndBySocietyId(@RequestBody RequestDtoForGettingEmployeesBySocietyId dto){
        List<CompanyEmployeeDto> companyEmployeeDto = companyEmployeeService.getListOfEmployeesHavingSalaryLessThanAndBySocietyId(dto);
        return new ResponseEntity<>(companyEmployeeDto, HttpStatus.OK);
    }

//    @PostMapping("/getEmployeeListByKeywordAndCompanyId")
//    private ResponseEntity<?> getEmployeeListByKeywordAndCompanyId(@RequestBody RequestDtoToGetEmployeeListByKeywordAndCompanyId dto){
//        Page<CompanyEmployeeDto> companyEmployeeDtoPage = companyEmployeeService.getEmployeeListByKeywordAndCompanyId(dto);
//        List<CompanyEmployeeDto> companyEmployeeDtoList = companyEmployeeDtoPage.getContent();
//        if(dto.getCompanyId() > companyEmployeeDtoPage.getTotalPages()){
//            throw new NotFoundException(HttpStatus.NOT_FOUND, "No further page available");
//        }
//        return new ResponseEntity<>(companyEmployeeDtoList, HttpStatus.OK);
//    }

}
