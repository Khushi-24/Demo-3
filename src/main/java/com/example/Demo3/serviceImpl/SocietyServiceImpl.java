package com.example.Demo3.serviceImpl;

import com.example.Demo3.dtos.AreaDto;
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

    @Override
    public Page<SocietyDto> getAllSociety(int pageNo) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNo -1, pageSize);
        Page<Society> societies = societyRepository.findAll(pageable);
        List<SocietyDto> societyDtoList = societies.stream().map((Society society) ->
                new SocietyDto(
                        society.getSocietyId(),
                        society.getSocietyName())).collect(Collectors.toList());
        return new PageImpl<>(societyDtoList,  pageable, societyDtoList.size());
    }
}
