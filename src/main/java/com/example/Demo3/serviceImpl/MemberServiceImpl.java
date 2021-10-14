package com.example.Demo3.serviceImpl;

import com.example.Demo3.dtos.*;
import com.example.Demo3.entities.Family;
import com.example.Demo3.entities.Members;
import com.example.Demo3.exception.BadRequestException;
import com.example.Demo3.exception.NotFoundException;
import com.example.Demo3.repository.FamilyRepository;
import com.example.Demo3.repository.MemberRepository;
import com.example.Demo3.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final FamilyRepository familyRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public MemberDto addMember(MemberDto memberDto) {
        Family family = familyRepository.findById(memberDto.getFamilyDto().getFamilyId()).orElseThrow(() ->
                new NotFoundException(HttpStatus.NOT_FOUND, "Family with familyId "
                + memberDto.getFamilyDto().getFamilyId() + " doesn't exists."));
        if(memberRepository.countByFamilyFamilyId(family.getFamilyId()) < family.getFamilyMembers() ){
            Members members = new Members();
            modelMapper.map(memberDto, members);
            memberRepository.save(members);
            return memberDto;
        }else {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "Members can't exceed family size.");
        }
    }

    @Override
    public MemberDto getMemberByMemberId(Long memberId) {
        Members members = memberRepository.findById(memberId).orElseThrow(()-> new NotFoundException(HttpStatus.NOT_FOUND,
                "Member doesn't exists."));
        MemberDto memberDto = new MemberDto();
        modelMapper.map(members, memberDto);
//        FamilyDto familyDto = memberDto.getFamilyDto();
//        SocietyDto societyDto = familyDto.getSocietyDto();
//        societyDto.setSocietyAddress(null);
//        societyDto.setUserDto(null);
//        AreaDto areaDto = societyDto.getAreaDto();
//        CityDto cityDto = areaDto.getCityDto();
//        cityDto.setCityState(null);
//        areaDto.setCityDto(cityDto);
//        societyDto.setAreaDto(areaDto);
//        familyDto.setSocietyDto(societyDto);
//        memberDto.setFamilyDto(familyDto);
        return memberDto;
    }
}
