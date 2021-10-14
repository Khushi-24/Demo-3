package com.example.Demo3.serviceImpl;

import com.example.Demo3.dtos.*;
import com.example.Demo3.entities.City;
import com.example.Demo3.entities.Family;
import com.example.Demo3.entities.Members;
import com.example.Demo3.exception.BadRequestException;
import com.example.Demo3.exception.NotFoundException;
import com.example.Demo3.repository.FamilyRepository;
import com.example.Demo3.repository.MemberRepository;
import com.example.Demo3.service.MemberService;
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

    @Override
    public Page<MemberDto> getAllMembers(int pageNo) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNo -1, pageSize);
        Page<Members> members = memberRepository.findAll(pageable);
        List<MemberDto> memberDtoList = members.stream().map((Members member) ->
                new MemberDto(
                        member.getMemberId(),
                        member.getMemberName())).collect(Collectors.toList());
        return new PageImpl<>(memberDtoList,  pageable, memberDtoList.size());
    }
}
