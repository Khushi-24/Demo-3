package com.example.Demo3.service;

import com.example.Demo3.dtos.FamilyDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Locale;

public interface FamilyService {
    FamilyDto addFamily(FamilyDto familyDto, Locale locale);

    FamilyDto getFamilyByFamilyId(Long familyId, Locale locale);

    Page<FamilyDto> getAllFamilies(int pageNo, Locale locale);

    List<FamilyDto> getAllFamiliesBySocietyId(Long societyId, Locale locale);
}
