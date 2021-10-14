package com.example.Demo3.service;

import com.example.Demo3.dtos.MemberDto;
import org.springframework.data.domain.Page;

public interface MemberService {
    MemberDto addMember(MemberDto memberDto);

    MemberDto getMemberByMemberId(Long memberId);

    Page<MemberDto> getAllMembers(int pageNo);
}
