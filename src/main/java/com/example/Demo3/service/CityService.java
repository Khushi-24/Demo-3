package com.example.Demo3.service;

import com.example.Demo3.dtos.CityDto;
import org.springframework.data.domain.Page;

import java.util.Locale;

public interface CityService {
    CityDto addCity(CityDto cityDto, Locale locale);

    Page<CityDto> getAllCities(int pageNo);

    CityDto getCityById(Long cityId, Locale locale);
}
