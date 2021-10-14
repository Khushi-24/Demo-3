package com.example.Demo3.service;

import com.example.Demo3.dtos.SocietyDto;
import org.springframework.data.domain.Page;

public interface SocietyService {
    SocietyDto addSociety(SocietyDto societyDto);

    Page<SocietyDto> getAllSociety(int pageNo);

    SocietyDto getSocietyBySocietyId(Long societyId);
}
