package com.example.Demo3.service;

import com.example.Demo3.dtos.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Locale;

public interface CompanyEmployeeService {
    CompanyEmployeeDto addMemberToCompany(CompanyEmployeeDto companyEmployeeDto, Locale locale);

    List<CompanyEmployeeDto> getListOfEmployeesHavingSalaryGreaterByAreaId(RequestDtoForGettingEmployeesByAreaId dto, Locale locale);

    List<CompanyEmployeeDto> getListOfEmployeesHavingSalaryGreaterThanAndByCityId(RequestDtoForGettingEmployeesByCityId dto, Locale locale);

    List<CompanyEmployeeDto> getListOfEmployeesHavingSalaryGreaterThanAndBySocietyId(RequestDtoForGettingEmployeesBySocietyId dto, Locale locale);

    Page<CompanyEmployeeDto> getEmployeeListByKeywordAndCompanyId(RequestDtoToGetEmployeeListByKeywordAndCompanyId dto);

    void deleteEmployeeFromCompanyByEmployeeIdAndCompanyId(RequestDtoForEmployeeIdAndCompanyId dto, Locale locale);
}
