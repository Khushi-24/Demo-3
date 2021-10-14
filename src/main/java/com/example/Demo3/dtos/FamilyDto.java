package com.example.Demo3.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FamilyDto {

    private Long familyId;

    @NotNull(message = "Size of family is required.")
    @Size(max = 25, message = "Max. size of family can be 25")
    @Size(min = 2, message = "Min. size of family can be 2")
    private Long familyMembers;

    @NotNull(message = "Society Id is required.")
    private SocietyDto societyDto;

    public FamilyDto(Long familyId, Long familyMembers) {
        this.familyId = familyId;
        this.familyMembers = familyMembers;
    }
}
