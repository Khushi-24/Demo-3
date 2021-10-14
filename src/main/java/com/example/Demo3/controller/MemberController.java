package com.example.Demo3.controller;

import com.example.Demo3.dtos.MemberDto;
import com.example.Demo3.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/addMember")
    public ResponseEntity<?> addMember(@RequestBody MemberDto memberDto){
        MemberDto dto = memberService.addMember(memberDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
