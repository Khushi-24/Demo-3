package com.example.Demo3.serviceImpl;

import com.example.Demo3.dtos.*;
import com.example.Demo3.entities.Area;
import com.example.Demo3.entities.City;
import com.example.Demo3.entities.Family;
import com.example.Demo3.entities.Society;
import com.example.Demo3.exception.BadRequestException;
import com.example.Demo3.exception.NotFoundException;
import com.example.Demo3.repository.AreaRepository;
import com.example.Demo3.repository.CityRepository;
import com.example.Demo3.repository.FamilyRepository;
import com.example.Demo3.repository.SocietyRepository;
import com.example.Demo3.service.FamilyService;
import com.example.Demo3.service.MailService;
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
public class FamilyServiceImpl implements FamilyService {

    private final AreaRepository areaRepository;

    private final CityRepository cityRepository;

    private final FamilyRepository familyRepository;

    private final SocietyRepository societyRepository;

    private final MailService mailService;

    private final ModelMapper modelMapper = new ModelMapper();


    @Override
    public FamilyDto addFamily(FamilyDto familyDto) {
        Society society = societyRepository.findById(familyDto.getSocietyId()).orElseThrow(()->
                new NotFoundException(HttpStatus.NOT_FOUND, "Society doesn't exists with society Id " +familyDto.getSocietyId()));
        Family family = modelMapper.map(familyDto, Family.class);
        MailDto mail = new MailDto();
        mail.setMailFrom("jiyanikhushali24@gmail.com");
        mail.setMailTo(society.getSocietyAdminEmail());
        mail.setMailSubject("Regarding Families of Society");
        mail.setMailContent("You have added a new family to your society with " +familyDto.getFamilyMembers()+ " members.");
        mailService.sendEmail(mail);
        familyRepository.save(family);
        return familyDto;
    }

    @Override
    public FamilyDto getFamilyByFamilyId(Long familyId) {
        Family family = familyRepository.findById(familyId).orElseThrow(()-> new NotFoundException(HttpStatus.NOT_FOUND,
                "Family doesn't exists"));
        FamilyDto familyDto = modelMapper.map(family, FamilyDto.class);
        SocietyDto societyDto = modelMapper.map(family.getSociety(), SocietyDto.class);
        societyDto.setSocietyAddress(null);
        AreaDto areaDto = modelMapper.map(family.getSociety().getArea(), AreaDto.class);
        CityDto cityDto = modelMapper.map(family.getSociety().getArea().getCity(), CityDto.class);
        cityDto.setCityState(null);
        areaDto.setCityId(null);
        areaDto.setCityDto(cityDto);
        societyDto.setAreaId(null);
        societyDto.setAreaDto(areaDto);
        familyDto.setSocietyDto(societyDto);
        familyDto.setSocietyId(null);
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
        return new PageImpl<>(familyDtoList,  pageable, families.getTotalElements());
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
