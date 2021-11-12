package com.example.Demo3.service;

import com.example.Demo3.dtos.AreaDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Locale;

public interface AreaService {
    AreaDto addArea(AreaDto areaDto, Locale locale);

    Page<AreaDto> getAllArea(int pageNo);

    AreaDto getAreaByAreaId(Long areaId, Locale locale);

    List<AreaDto> getListOfAreaByCityId(Long cityId, Locale locale);
}
