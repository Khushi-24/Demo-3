package com.example.Demo3.service;

import com.example.Demo3.dtos.MemberDto;
import com.example.Demo3.dtos.RequestDtoForMembersHavingAgeLessThanByAreaId;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MemberService {
    MemberDto addMember(MemberDto memberDto);

    MemberDto getMemberByMemberId(Long memberId);

    Page<MemberDto> getAllMembers(int pageNo);

    List<MemberDto> getAllMembersByFamilyId(Long familyId);

    List<MemberDto> getAllMembersHavingAgeLessThanByAreaId(RequestDtoForMembersHavingAgeLessThanByAreaId dto);
}
