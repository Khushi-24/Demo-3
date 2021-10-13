package com.example.Demo3.service;

import com.example.Demo3.dtos.AreaDto;
import org.springframework.data.domain.Page;

public interface AreaService {
    AreaDto addArea(AreaDto areaDto);

    Page<AreaDto> getAllArea(int pageNo);
}
