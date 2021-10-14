package com.example.Demo3.serviceImpl;

import com.example.Demo3.dtos.*;
import com.example.Demo3.entities.City;
import com.example.Demo3.entities.Family;
import com.example.Demo3.entities.Members;
import com.example.Demo3.entities.Society;
import com.example.Demo3.exception.BadRequestException;
import com.example.Demo3.exception.NotFoundException;
import com.example.Demo3.repository.FamilyRepository;
import com.example.Demo3.repository.SocietyRepository;
import com.example.Demo3.service.FamilyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FamilyServiceImpl implements FamilyService {

    private final FamilyRepository familyRepository;

    private final SocietyRepository societyRepository;

    private final ModelMapper modelMapper = new ModelMapper();


    @Override
    public FamilyDto addFamily(FamilyDto familyDto) {
        Society society = societyRepository.findById(familyDto.getSocietyDto().getSocietyId()).orElseThrow(()->
                new NotFoundException(HttpStatus.NOT_FOUND, "Society doesn't exists with society Id " +familyDto.getSocietyDto().getSocietyId()));
        Family family = new Family();
        modelMapper.map(familyDto, family);
        familyRepository.save(family);
        return familyDto;
    }

    @Override
    public FamilyDto getFamilyByFamilyId(Long familyId) {
        Family family = familyRepository.findById(familyId).orElseThrow(()-> new NotFoundException(HttpStatus.NOT_FOUND,
                "Family doesn't exists"));
        FamilyDto familyDto = new FamilyDto();
        modelMapper.map(family, familyDto);
//        SocietyDto societyDto = familyDto.getSocietyDto();
//        societyDto.setSocietyAddress(null);
//        societyDto.setUserDto(null);
//        AreaDto areaDto = societyDto.getAreaDto();
//        CityDto cityDto = areaDto.getCityDto();
//        cityDto.setCityState(null);
//        areaDto.setCityDto(cityDto);
//        societyDto.setAreaDto(areaDto);
//        familyDto.setSocietyDto(societyDto);
        return familyDto;
    }

    @Override
    public Page<FamilyDto> getAllFamilies(int pageNo) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNo -1, pageSize);
        Page<Family> families = familyRepository.findAll(pageable);
        List<FamilyDto> familyDtoList = families.stream().map((Family family) ->
                new FamilyDto(
                        family.getFamilyId(),
                        family.getFamilyMembers())).collect(Collectors.toList());
        return new PageImpl<>(familyDtoList,  pageable, familyDtoList.size());
    }

    @Override
    public List<FamilyDto> getAllFamiliesBySocietyId(Long societyId) {
        if(societyId != null){
            Society society = societyRepository.findById(societyId).orElseThrow(()-> new NotFoundException(HttpStatus.NOT_FOUND,
                    "Society doesn't exists."));
            List<Family>  familyList = familyRepository.findAllBySocietySocietyId(societyId);
            List<FamilyDto> familyDtoList = familyList.stream().map((Family family) ->
                    new FamilyDto(
                            family.getFamilyId(),
                            family.getFamilyMembers())).collect(Collectors.toList());
            return familyDtoList;
        }else{
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "Society Id can't be null.");
        }

    }

}
