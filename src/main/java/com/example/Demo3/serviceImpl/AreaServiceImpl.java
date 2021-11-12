package com.example.Demo3.serviceImpl;

import com.example.Demo3.dtos.AreaDto;
import com.example.Demo3.dtos.CityDto;
import com.example.Demo3.entities.Area;
import com.example.Demo3.entities.City;
import com.example.Demo3.exception.AlreadyExistsException;
import com.example.Demo3.exception.BadRequestException;
import com.example.Demo3.exception.NotFoundException;
import com.example.Demo3.repository.AreaRepository;
import com.example.Demo3.repository.CityRepository;
import com.example.Demo3.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AreaServiceImpl implements AreaService {

    private final AreaRepository areaRepository;

    private final CityRepository cityRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    private final MessageSource messageSource;

    @Override
    public AreaDto addArea(AreaDto areaDto, Locale locale) {
        City city = cityRepository.findById(areaDto.getCityId()).orElseThrow(() -> new
                NotFoundException(HttpStatus.NOT_FOUND, messageSource.getMessage("city_does_not_exists.message",
                null,
                locale)));
        if(!areaRepository.existsById(areaDto.getAreaId())){
            Area area = modelMapper.map(areaDto, Area.class);
            areaRepository.save(area);
            return areaDto;
        }else {
            throw new AlreadyExistsException(HttpStatus.CONFLICT, messageSource.getMessage("area_already_exists.message",
                    null,
                    locale));
        }

    }

    @Override
    public Page <AreaDto> getAllArea(int pageNo) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNo -1, pageSize);
        Page<Area> cities = areaRepository.findAll(pageable);
        List<AreaDto> cityDtoList = cities.stream().map((Area area) ->
                new AreaDto(
                        area.getAreaId(),
                        area.getAreaName())).collect(Collectors.toList());
        return new PageImpl<>(cityDtoList,  pageable, cityDtoList.size());
    }

    @Override
    public AreaDto getAreaByAreaId(Long areaId, Locale locale) {
        Area area = areaRepository.findById(areaId).orElseThrow(()-> new NotFoundException(HttpStatus.NOT_FOUND,
                messageSource.getMessage("area_does_not_exists.message",
                        null,
                        locale)));
        CityDto cityDto = modelMapper.map(area.getCity(), CityDto.class);
        cityDto.setCityState(null);
        AreaDto areaDto = modelMapper.map(area, AreaDto.class);
        areaDto.setCityId(null);
        areaDto.setCityDto(cityDto);
        return areaDto;
    }

    @Override
    public List<AreaDto> getListOfAreaByCityId(Long cityId, Locale locale) {
        if(cityId != null){
            City city = cityRepository.findById(cityId).orElseThrow(()-> new NotFoundException(HttpStatus.NOT_FOUND,
                    messageSource.getMessage("city_does_not_exists.message",
                            null,
                            locale)));
            List<Area> areaList = areaRepository.findAllByCityCityId(cityId);
            return areaList.stream().map((Area area)-> new AreaDto(
                    area.getAreaId(),
                    area.getAreaName())).collect(Collectors.toList());
        }
        else {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, messageSource.getMessage("city_id_cannot_be_null.message",
                    null,
                    locale));
        }
    }
}
