package com.example.Demo3.service;

import com.example.Demo3.dtos.FamilyDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FamilyService {
    FamilyDto addFamily(FamilyDto familyDto);

    FamilyDto getFamilyByFamilyId(Long familyId);

    Page<FamilyDto> getAllFamilies(int pageNo);

    List<FamilyDto> getAllFamiliesBySocietyId(Long societyId);
}
