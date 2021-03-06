package com.example.Demo3.serviceImpl;

import com.example.Demo3.dtos.*;
import com.example.Demo3.entities.*;
import com.example.Demo3.exception.BadRequestException;
import com.example.Demo3.exception.NotFoundException;
import com.example.Demo3.repository.*;
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
    private final AreaRepository areaRepository;

    private final CityRepository cityRepository;

    private final SocietyRepository societyRepository;

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
        memberDto.setMemberAge(null);
        memberDto.setIsWorking(null);
        Family family = familyRepository.findById(members.getFamily().getFamilyId()).get();
        FamilyDto familyDto = new FamilyDto();
        modelMapper.map(family, familyDto);
        Society society = societyRepository.findById(family.getSociety().getSocietyId()).get();
        society.setSocietyAddress(null);
        society.setSocietyAdminEmail(null);
        SocietyDto societyDto = new SocietyDto();
        modelMapper.map(society, societyDto);
        Area area = areaRepository.findById(society.getArea().getAreaId()).get();
        AreaDto areaDto = new AreaDto();
        modelMapper.map(area, areaDto);
        City city = cityRepository.findById(area.getCity().getCityId()).get();
        CityDto cityDto = new CityDto();
        modelMapper.map(city, cityDto);
        cityDto.setCityState(null);
        areaDto.setCityDto(cityDto);
        societyDto.setAreaDto(areaDto);
        familyDto.setSocietyDto(societyDto);
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
        return new PageImpl<>(memberDtoList,  pageable, memberDtoList.size());
    }

    @Override
    public List<MemberDto> getAllMembersByFamilyId(Long familyId) {
        if(familyId != null){
            Family family = familyRepository.findById(familyId).orElseThrow(()-> new NotFoundException(HttpStatus.NOT_FOUND,
                    "Family doesn't exists"));
            List<Members> membersList = memberRepository.findAllByFamilyFamilyId(familyId);
            List<MemberDto> memberDtoList = membersList.stream().map((Members member) ->
                    new MemberDto(
                            member.getMemberId(),
                            member.getMemberName())).collect(Collectors.toList());
            return memberDtoList;
        }
        else {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "Family Id can't be null.");
        }
    }

    @Override
    public List<MemberDto> getAllMembersHavingAgeLessThanByAreaId(RequestDtoForMembersHavingAgeLessThanByAreaId dto) {
        List<Members> membersList = memberRepository.getAllMembersHavingAgeLessThanByAreaId(dto.getAreaId(), dto.getAge());
        List<MemberDto> memberDtoList = membersList.stream().map((Members member) ->
                new MemberDto(
                        member.getMemberId(),
                        member.getMemberName(),
                        member.getMemberAge())).collect(Collectors.toList());
        return memberDtoList;
    }

    @Override
    public List<MemberDto> getAllMembersHavingAgeLessThanByCityId(RequestDtoForMembersHavingAgeLessThanByCityId dto) {
        List<Members> membersList = memberRepository.getAllMembersHavingAgeLessThanByCityId(dto.getCityId(), dto.getAge());
        List<MemberDto> memberDtoList = membersList.stream().map((Members member) ->
                new MemberDto(
                        member.getMemberId(),
                        member.getMemberName(),
                        member.getMemberAge())).collect(Collectors.toList());
        return memberDtoList;
    }

    @Override
    public List<MemberDto> getAllMembersHavingAgeLessThanBySocietyId(RequestDtoForMembersHavingAgeLessThanBySocietyId dto) {
        List<Members> membersList = memberRepository.getAllMembersHavingAgeLessThanBySocietyId(dto.getSocietyId(), dto.getAge());
        List<MemberDto> memberDtoList = membersList.stream().map((Members member) ->
                new MemberDto(
                        member.getMemberId(),
                        member.getMemberName(),
                        member.getMemberAge())).collect(Collectors.toList());
        return memberDtoList;
    }
}
