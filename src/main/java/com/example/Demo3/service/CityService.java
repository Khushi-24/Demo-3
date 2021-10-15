package com.example.Demo3.service;

import com.example.Demo3.dtos.CityDto;
import org.springframework.data.domain.Page;

public interface CityService {
    CityDto addCity(CityDto cityDto);

    Page<CityDto> getAllCities(int pageNo);

    CityDto getCityById(Long cityId);
}
