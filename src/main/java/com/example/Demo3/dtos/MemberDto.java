package com.example.Demo3.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {


    private String memberName;

    private Long memberAge;

    private Boolean isFamilyAdmin;

    private FamilyDto familyDto;
}
