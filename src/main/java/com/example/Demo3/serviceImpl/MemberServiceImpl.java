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
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final FamilyRepository familyRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    private final MessageSource messageSource;

    @Override
    public MemberDto addMember(MemberDto memberDto, Locale locale) {
        Family family = familyRepository.findById(memberDto.getFamilyId()).orElseThrow(() ->
                new NotFoundException(HttpStatus.NOT_FOUND, messageSource.getMessage("family_does_not_exists.message", null, locale)));
        if(memberRepository.countByFamilyFamilyId(family.getFamilyId()) < family.getFamilyMembers() ){
            Members members = modelMapper.map(memberDto, Members.class);
            memberRepository.save(members);
            return memberDto;
        }else {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, messageSource.getMessage("members_cannot_exceed_family_size.message", null, locale));
        }
    }

    @Override
    public MemberDto getMemberByMemberId(Long memberId, Locale locale) {
        Members members = memberRepository.findById(memberId).orElseThrow(()-> new NotFoundException(HttpStatus.NOT_FOUND,
                messageSource.getMessage("member_does_not_exists.message", null, locale)));
        MemberDto memberDto = modelMapper.map(members, MemberDto.class);
        memberDto.setMemberAge(null);
        memberDto.setIsWorking(null);
        memberDto.setFamilyId(null);
        FamilyDto familyDto = modelMapper.map(members.getFamily(), FamilyDto.class);
        SocietyDto societyDto = modelMapper.map(members.getFamily().getSociety(), SocietyDto.class);
        societyDto.setSocietyAddress(null);
        AreaDto areaDto = modelMapper.map(members.getFamily().getSociety().getArea(), AreaDto.class);
        CityDto cityDto = modelMapper.map(members.getFamily().getSociety().getArea().getCity(), CityDto.class);
        cityDto.setCityState(null);
        areaDto.setCityDto(cityDto);
        areaDto.setCityId(null);
        societyDto.setAreaDto(areaDto);
        societyDto.setAreaId(null);
        familyDto.setSocietyDto(societyDto);
        familyDto.setSocietyId(null);
        memberDto.setFamilyDto(familyDto);
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
        return new PageImpl<>(memberDtoList,  pageable, members.getTotalElements());
    }

    @Override
    public List<MemberDto> getAllMembersByFamilyId(Long familyId, Locale locale) {
        if(familyId != null){
            Family family = familyRepository.findById(familyId).orElseThrow(()-> new NotFoundException(HttpStatus.NOT_FOUND,
                    messageSource.getMessage("family_does_not_exists.message", null, locale)));
            List<Members> membersList = memberRepository.findAllByFamilyFamilyId(familyId);
            return membersList.stream().map((Members member) ->
                    new MemberDto(
                            member.getMemberId(),
                            member.getMemberName())).collect(Collectors.toList());
        }
        else {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, messageSource.getMessage("family_id_cannot_be_null", null, locale));
        }
    }

    @Override
    public List<MemberDto> getAllMembersHavingAgeLessThanByAreaId(RequestDtoForMembersHavingAgeLessThanByAreaId dto) {
        List<Members> membersList = memberRepository.getAllMembersHavingAgeLessThanByAreaId(dto.getAreaId(), dto.getAge());
        return membersList.stream().map((Members member) ->
                new MemberDto(
                        member.getMemberId(),
                        member.getMemberName(),
                        member.getMemberAge())).collect(Collectors.toList());
    }

    @Override
    public List<MemberDto> getAllMembersHavingAgeLessThanByCityId(RequestDtoForMembersHavingAgeLessThanByCityId dto) {
        List<Members> membersList = memberRepository.getAllMembersHavingAgeLessThanByCityId(dto.getCityId(), dto.getAge());
        return membersList.stream().map((Members member) ->
                new MemberDto(
                        member.getMemberId(),
                        member.getMemberName(),
                        member.getMemberAge())).collect(Collectors.toList());
    }

    @Override
    public List<MemberDto> getAllMembersHavingAgeLessThanBySocietyId(RequestDtoForMembersHavingAgeLessThanBySocietyId dto) {
        List<Members> membersList = memberRepository.getAllMembersHavingAgeLessThanBySocietyId(dto.getSocietyId(), dto.getAge());
        return membersList.stream().map((Members member) ->
                new MemberDto(
                        member.getMemberId(),
                        member.getMemberName(),
                        member.getMemberAge())).collect(Collectors.toList());
    }
}
