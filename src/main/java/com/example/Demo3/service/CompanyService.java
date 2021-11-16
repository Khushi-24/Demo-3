package com.example.Demo3.service;

import com.example.Demo3.dtos.CompanyDto;
import org.springframework.data.domain.Page;

import java.util.Locale;

public interface CompanyService {
    CompanyDto addCompany(CompanyDto companyDto, Locale locale);

    Page<CompanyDto> getAllCompanies(int pageNo, Locale locale);
}
