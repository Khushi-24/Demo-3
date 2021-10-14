package com.example.Demo3.serviceImpl;

import com.example.Demo3.dtos.MemberDto;
import com.example.Demo3.entities.Family;
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

        }
        return null;
    }
}
