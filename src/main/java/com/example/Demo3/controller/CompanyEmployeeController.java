package com.example.Demo3.controller;

import com.example.Demo3.dtos.*;
import com.example.Demo3.exception.NotFoundException;
import com.example.Demo3.service.CompanyEmployeeService;
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
public class CompanyEmployeeController {

    private final CompanyEmployeeService companyEmployeeService;

    private final MessageSource messageSource;

    @PostMapping("/addMemberToCompany")
    @PreAuthorize("hasAnyRole('Admin','Company Admin')")
    public ResponseEntity<?> addMemberToCompany(@RequestBody CompanyEmployeeDto companyEmployeeDto, Locale locale){
        CompanyEmployeeDto dto = companyEmployeeService.addMemberToCompany(companyEmployeeDto, locale);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/getListOfEmployeesHavingSalaryGreaterThanByAreaId")
    @PreAuthorize("hasAnyRole('Admin','Company Admin')")
    public ResponseEntity<?> getListOfEmployeesHavingSalaryGreaterThanByAreaId(@RequestBody RequestDtoForGettingEmployeesByAreaId dto, Locale locale){
        List<CompanyEmployeeDto> companyEmployeeDto = companyEmployeeService.getListOfEmployeesHavingSalaryGreaterByAreaId(dto, locale);
        return new ResponseEntity<>(companyEmployeeDto, HttpStatus.OK);
    }

    @GetMapping("/getListOfEmployeesHavingSalaryGreaterThanAndByCityId")
    @PreAuthorize("hasAnyRole('Admin','Company Admin')")
    public ResponseEntity<?> getListOfEmployeesHavingSalaryGreaterThanByCityId(@RequestBody RequestDtoForGettingEmployeesByCityId dto, Locale locale){
        List<CompanyEmployeeDto> companyEmployeeDto = companyEmployeeService.getListOfEmployeesHavingSalaryGreaterThanAndByCityId(dto, locale);
        return new ResponseEntity<>(companyEmployeeDto, HttpStatus.OK);
    }

    @GetMapping("/getListOfEmployeesHavingSalaryGreaterThanAndBySocietyId")
    @PreAuthorize("hasAnyRole('Admin','Company Admin')")
    public ResponseEntity<?> getListOfEmployeesHavingSalaryGreaterThanAndBySocietyId(@RequestBody RequestDtoForGettingEmployeesBySocietyId dto, Locale locale){
        List<CompanyEmployeeDto> companyEmployeeDto = companyEmployeeService.getListOfEmployeesHavingSalaryGreaterThanAndBySocietyId(dto, locale);
        return new ResponseEntity<>(companyEmployeeDto, HttpStatus.OK);
    }

    @PostMapping("/getEmployeeListByKeywordAndCompanyId")
    public ResponseEntity<?> getEmployeeListByKeywordAndCompanyId(@RequestBody RequestDtoToGetEmployeeListByKeywordAndCompanyId dto, Locale locale){
        Page<CompanyEmployeeDto> companyEmployeeDtoPage = companyEmployeeService.getEmployeeListByKeywordAndCompanyId(dto);
        List<CompanyEmployeeDto> companyEmployeeDtoList = companyEmployeeDtoPage.getContent();
        if(dto.getCompanyId() > companyEmployeeDtoPage.getTotalPages()){
            throw new NotFoundException(HttpStatus.NOT_FOUND, messageSource.getMessage("no_further_page_available.message", null, locale));
        }
        return new ResponseEntity<>(companyEmployeeDtoList, HttpStatus.OK);
    }

    @DeleteMapping("/deleteEmployeeFromCompanyByEmployeeIdAndCompanyId")
    public ResponseEntity<?> deleteEmployeeFromCompanyByEmployeeIdAndCompanyId(@RequestBody RequestDtoForEmployeeIdAndCompanyId dto, Locale locale){
        companyEmployeeService.deleteEmployeeFromCompanyByEmployeeIdAndCompanyId(dto, locale);
        return new ResponseEntity<>(messageSource.getMessage("employee_deleted_successfully.message", null, locale) ,HttpStatus.OK);
    }
}
