package com.example.Demo3.service;

import com.example.Demo3.dtos.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CompanyEmployeeService {
    CompanyEmployeeDto addMemberToCompany(CompanyEmployeeDto companyEmployeeDto);

    List<CompanyEmployeeDto> getListOfEmployeesHavingSalaryGreaterByAreaId(RequestDtoForGettingEmployeesByAreaId dto);

    List<CompanyEmployeeDto> getListOfEmployeesHavingSalaryGreaterThanAndByCityId(RequestDtoForGettingEmployeesByCityId dto);

    List<CompanyEmployeeDto> getListOfEmployeesHavingSalaryGreaterThanAndBySocietyId(RequestDtoForGettingEmployeesBySocietyId dto);

    Page<CompanyEmployeeDto> getEmployeeListByKeywordAndCompanyId(RequestDtoToGetEmployeeListByKeywordAndCompanyId dto);

    void deleteEmployeeFromCompanyByEmployeeIdAndCompanyId(RequestDtoForEmployeeIdAndCompanyId dto);
}
