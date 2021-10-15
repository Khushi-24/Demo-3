package com.example.Demo3.service;

import com.example.Demo3.dtos.CompanyDto;
import org.springframework.data.domain.Page;

public interface CompanyService {
    CompanyDto addCompany(CompanyDto companyDto);

    Page<CompanyDto> getAllCompanies(int pageNo);
}
