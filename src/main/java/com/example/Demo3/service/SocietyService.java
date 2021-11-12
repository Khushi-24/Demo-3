package com.example.Demo3.service;

import com.example.Demo3.dtos.SocietyDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Locale;

public interface SocietyService {
    SocietyDto addSociety(SocietyDto societyDto, Locale locale);

    Page<SocietyDto> getAllSociety(int pageNo);

    SocietyDto getSocietyBySocietyId(Long societyId, Locale locale);

    List<SocietyDto> getSocietyByAreaId(Long areaId, Locale locale);
}
