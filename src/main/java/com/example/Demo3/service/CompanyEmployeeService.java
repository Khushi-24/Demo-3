package com.example.Demo3.service;

import com.example.Demo3.dtos.CompanyEmployeeDto;
import com.example.Demo3.dtos.RequestDtoForGettingEmployeesByAreaId;
import com.example.Demo3.dtos.RequestDtoForGettingEmployeesByCityId;
import com.example.Demo3.dtos.RequestDtoForGettingEmployeesBySocietyId;

import java.util.List;

public interface CompanyEmployeeService {
    CompanyEmployeeDto addMemberToCompany(CompanyEmployeeDto companyEmployeeDto);

    List<CompanyEmployeeDto> getListOfEmployeesHavingSalaryLessByAreaId(RequestDtoForGettingEmployeesByAreaId dto);

    List<CompanyEmployeeDto> getListOfEmployeesHavingSalaryLessThanAndByCityId(RequestDtoForGettingEmployeesByCityId dto);

    List<CompanyEmployeeDto> getListOfEmployeesHavingSalaryLessThanAndBySocietyId(RequestDtoForGettingEmployeesBySocietyId dto);
}
