package com.example.Demo3.service;

import com.example.Demo3.dtos.MemberDto;
import com.example.Demo3.dtos.RequestDtoForMembersHavingAgeLessThanByAreaId;
import com.example.Demo3.dtos.RequestDtoForMembersHavingAgeLessThanByCityId;
import com.example.Demo3.dtos.RequestDtoForMembersHavingAgeLessThanBySocietyId;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Locale;

public interface MemberService {
    MemberDto addMember(MemberDto memberDto, Locale locale);

    MemberDto getMemberByMemberId(Long memberId, Locale locale);

    Page<MemberDto> getAllMembers(int pageNo);

    List<MemberDto> getAllMembersByFamilyId(Long familyId, Locale locale);

    List<MemberDto> getAllMembersHavingAgeLessThanByAreaId(RequestDtoForMembersHavingAgeLessThanByAreaId dto);

    List<MemberDto> getAllMembersHavingAgeLessThanByCityId(RequestDtoForMembersHavingAgeLessThanByCityId dto);

    List<MemberDto> getAllMembersHavingAgeLessThanBySocietyId(RequestDtoForMembersHavingAgeLessThanBySocietyId dto);
}
