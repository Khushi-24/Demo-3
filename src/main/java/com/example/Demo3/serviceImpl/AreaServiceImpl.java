package com.example.Demo3.serviceImpl;

import com.example.Demo3.dtos.AreaDto;
import com.example.Demo3.dtos.CityDto;
import com.example.Demo3.entities.Area;
import com.example.Demo3.entities.City;
import com.example.Demo3.exception.AlreadyExistsException;
import com.example.Demo3.exception.NotFoundException;
import com.example.Demo3.repository.AreaRepository;
import com.example.Demo3.repository.CityRepository;
import com.example.Demo3.service.AreaService;
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
public class AreaServiceImpl implements AreaService {

    private final AreaRepository areaRepository;

    private final CityRepository cityRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public AreaDto addArea(AreaDto areaDto) {
        City city = cityRepository.findById(areaDto.getCityDto().getCityId()).orElseThrow(() -> new
                NotFoundException(HttpStatus.NOT_FOUND, "City doesn't exists with cityId = " +areaDto.getCityDto().getCityId()));
        if(!areaRepository.existsById(areaDto.getAreaId())){
            Area area = new Area();
            modelMapper.map(areaDto, area);
            areaRepository.save(area);
            return areaDto;
        }else {
            throw new AlreadyExistsException(HttpStatus.CONFLICT, "Area already Exists");
        }

    }

    @Override
    public Page<AreaDto> getAllArea(int pageNo) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNo -1, pageSize);
        Page<Area> cities = areaRepository.findAll(pageable);
        List<AreaDto> cityDtoList = cities.stream().map((Area area) ->
                new AreaDto(
                        area.getAreaId(),
                        area.getAreaName())).collect(Collectors.toList());
        return new PageImpl<>(cityDtoList,  pageable, cityDtoList.size());
    }
}
