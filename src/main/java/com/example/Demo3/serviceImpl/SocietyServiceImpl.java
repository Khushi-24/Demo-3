package com.example.Demo3.serviceImpl;

import com.example.Demo3.dtos.SocietyDto;
import com.example.Demo3.entities.Area;
import com.example.Demo3.entities.Society;
import com.example.Demo3.exception.AlreadyExistsException;
import com.example.Demo3.exception.NotFoundException;
import com.example.Demo3.repository.AreaRepository;
import com.example.Demo3.repository.SocietyRepository;
import com.example.Demo3.service.SocietyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SocietyServiceImpl implements SocietyService {

    private final AreaRepository areaRepository;

    private final SocietyRepository societyRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public SocietyDto addSociety(SocietyDto societyDto) {
        Area area = areaRepository.findById(societyDto.getAreaDto().getAreaId()).orElseThrow(() ->
                new NotFoundException(HttpStatus.NOT_FOUND, "No such area exists with area Id " + societyDto.getAreaDto().getAreaId()));
        Society society = new Society();
        modelMapper.map(societyDto, society);
        societyRepository.save(society);
        return societyDto;

    }
}
