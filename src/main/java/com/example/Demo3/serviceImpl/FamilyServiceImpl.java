package com.example.Demo3.serviceImpl;

import com.example.Demo3.dtos.FamilyDto;
import com.example.Demo3.dtos.MemberDto;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Set;

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
}
