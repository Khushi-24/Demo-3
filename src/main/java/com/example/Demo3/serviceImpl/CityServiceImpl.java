package com.example.Demo3.serviceImpl;

import com.example.Demo3.dtos.CityDto;
import com.example.Demo3.entities.City;
import com.example.Demo3.exception.AlreadyExistsException;
import com.example.Demo3.exception.NotFoundException;
import com.example.Demo3.repository.CityRepository;
import com.example.Demo3.service.CityService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public Page<CityDto> getAllCities(int pageNo) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNo -1, pageSize);
        Page<City> cities = cityRepository.findAll(pageable);
        List<CityDto> cityDtoList = cities.stream().map((City city) ->
                new CityDto(
                        city.getCityId(),
                        city.getCityName())).collect(Collectors.toList());
        return new PageImpl<>(cityDtoList,  pageable, cityDtoList.size());
    }

    @Override
    public CityDto getCityById(Long cityId) {
        City city = cityRepository.findById(cityId).orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND,
                "City doesn't exists"));
        CityDto cityDto = new CityDto();
        modelMapper.map(city, cityDto);
        return cityDto;
    }
}
