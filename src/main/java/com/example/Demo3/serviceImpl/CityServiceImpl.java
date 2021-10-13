package com.example.Demo3.serviceImpl;

import com.example.Demo3.dtos.CityDto;
import com.example.Demo3.entities.City;
import com.example.Demo3.exception.AlreadyExistsException;
import com.example.Demo3.repository.CityRepository;
import com.example.Demo3.service.CityService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public CityDto addCity(CityDto cityDto) {
        if(!cityRepository.existsById(cityDto.getCityId())){
            City city = new City();
            modelMapper.map(cityDto, city);
            cityRepository.save(city);
            return cityDto;
        }else{
            throw new AlreadyExistsException(HttpStatus.CONFLICT, "City already exists.");
        }
    }
}
