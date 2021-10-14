package com.example.Demo3.controller;

import com.example.Demo3.dtos.CityDto;
import com.example.Demo3.dtos.MemberDto;
import com.example.Demo3.exception.NotFoundException;
import com.example.Demo3.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/addMember")
    public ResponseEntity<?> addMember(@RequestBody MemberDto memberDto){
        MemberDto dto = memberService.addMember(memberDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/getMemberByMemberId/{memberId}")
    public ResponseEntity<?> getMemberByMemberId(@PathVariable Long memberId){
        MemberDto memberDto = memberService.getMemberByMemberId(memberId);
        return new ResponseEntity<>(memberDto, HttpStatus.OK);
    }

    @PostMapping("/getAllMembers/{pageNo}")
    public ResponseEntity<?> getAllMembers(@PathVariable int pageNo){
        Page<MemberDto> memberDtoPage = memberService.getAllMembers(pageNo);
        List<MemberDto> memberDtoList = memberDtoPage.getContent();
        if(pageNo> memberDtoPage.getTotalPages()){
            throw new NotFoundException(HttpStatus.NOT_FOUND, "No further page available");
        }
        return new ResponseEntity<>(memberDtoList, HttpStatus.OK);
    }
}
