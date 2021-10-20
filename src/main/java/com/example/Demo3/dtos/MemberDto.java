package com.example.Demo3.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberDto {

    private  Long memberId;

    @NotNull(message = "Member name is required.")
    @NotBlank(message = "Member name is required.")
    private String memberName;

    @NotNull(message = "Member name is required.")
    @NotBlank(message = "Member name is required.")
    private Long memberAge;

    @NotNull(message = "Mention whether member is working or not.")
    private Boolean isWorking;

    @NotNull(message = "Family Id is required")
    private FamilyDto familyDto;

    public MemberDto(Long memberId, String memberName) {
        this.memberId = memberId;
        this.memberName = memberName;
    }

    public MemberDto(Long memberId, String memberName, Long memberAge) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberAge = memberAge;
    }
}
