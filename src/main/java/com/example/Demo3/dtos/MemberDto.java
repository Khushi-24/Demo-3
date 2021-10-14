package com.example.Demo3.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MemberDto {

    @NotNull(message = "Member name is required.")
    @NotBlank(message = "Member name is required.")
    private String memberName;

    @NotNull(message = "Member name is required.")
    @NotBlank(message = "Member name is required.")
    private Long memberAge;

    @NotNull(message = "Family Id is required")
    private FamilyDto familyDto;
}
