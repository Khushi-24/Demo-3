package com.example.Demo3.service;

import com.example.Demo3.dtos.AreaDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AreaService {
    AreaDto addArea(AreaDto areaDto);

    Page<AreaDto> getAllArea(int pageNo);

    AreaDto getAreaByAreaId(Long areaId);

    List<AreaDto> getListOfAreaByCityId(Long cityId);
}
