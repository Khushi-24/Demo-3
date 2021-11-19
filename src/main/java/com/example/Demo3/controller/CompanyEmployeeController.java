package com.example.Demo3.controller;

import com.example.Demo3.dtos.*;
import com.example.Demo3.exception.NotFoundException;
import com.example.Demo3.service.CompanyEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CompanyEmployeeController {

    private final CompanyEmployeeService companyEmployeeService;

    @PostMapping("/addMemberToCompany")
    @PreAuthorize("hasAnyRole('Admin','Company Admin')")
    public ResponseEntity<?> addMemberToCompany(@RequestBody CompanyEmployeeDto companyEmployeeDto){
        CompanyEmployeeDto dto = companyEmployeeService.addMemberToCompany(companyEmployeeDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/getListOfEmployeesHavingSalaryGreaterThanByAreaId")
    @PreAuthorize("hasAnyRole('Admin','Company Admin')")
    public ResponseEntity<?> getListOfEmployeesHavingSalaryGreaterThanByAreaId(@RequestBody RequestDtoForGettingEmployeesByAreaId dto){
        List<CompanyEmployeeDto> companyEmployeeDto = companyEmployeeService.getListOfEmployeesHavingSalaryGreaterByAreaId(dto);
        return new ResponseEntity<>(companyEmployeeDto, HttpStatus.OK);
    }

    @GetMapping("/getListOfEmployeesHavingSalaryGreaterThanAndByCityId")
    @PreAuthorize("hasAnyRole('Admin','Company Admin')")
    public ResponseEntity<?> getListOfEmployeesHavingSalaryGreaterThanByCityId(@RequestBody RequestDtoForGettingEmployeesByCityId dto){
        List<CompanyEmployeeDto> companyEmployeeDto = companyEmployeeService.getListOfEmployeesHavingSalaryGreaterThanAndByCityId(dto);
        return new ResponseEntity<>(companyEmployeeDto, HttpStatus.OK);
    }

    @GetMapping("/getListOfEmployeesHavingSalaryGreaterThanAndBySocietyId")
    @PreAuthorize("hasAnyRole('Admin','Company Admin')")
    public ResponseEntity<?> getListOfEmployeesHavingSalaryGreaterThanAndBySocietyId(@RequestBody RequestDtoForGettingEmployeesBySocietyId dto){
        List<CompanyEmployeeDto> companyEmployeeDto = companyEmployeeService.getListOfEmployeesHavingSalaryGreaterThanAndBySocietyId(dto);
        return new ResponseEntity<>(companyEmployeeDto, HttpStatus.OK);
    }

    @PostMapping("/getEmployeeListByKeywordAndCompanyId")
    public ResponseEntity<?> getEmployeeListByKeywordAndCompanyId(@RequestBody RequestDtoToGetEmployeeListByKeywordAndCompanyId dto){
        Page<CompanyEmployeeDto> companyEmployeeDtoPage = companyEmployeeService.getEmployeeListByKeywordAndCompanyId(dto);
        List<CompanyEmployeeDto> companyEmployeeDtoList = companyEmployeeDtoPage.getContent();
        if(dto.getCompanyId() > companyEmployeeDtoPage.getTotalPages()){
            throw new NotFoundException(HttpStatus.NOT_FOUND, "No further page available");
        }
        return new ResponseEntity<>(companyEmployeeDtoList, HttpStatus.OK);
    }

    @DeleteMapping("/deleteEmployeeFromCompanyByEmployeeIdAndCompanyId")
    public ResponseEntity<?> deleteEmployeeFromCompanyByEmployeeIdAndCompanyId(@RequestBody RequestDtoForEmployeeIdAndCompanyId dto){
        companyEmployeeService.deleteEmployeeFromCompanyByEmployeeIdAndCompanyId(dto);
        return new ResponseEntity<>("Employee deleted successfully from company." ,HttpStatus.OK);
    }
}
