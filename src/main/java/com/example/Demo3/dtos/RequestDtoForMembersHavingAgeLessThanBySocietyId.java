package com.example.Demo3.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RequestDtoForMembersHavingAgeLessThanBySocietyId {

    @NotNull(message = "Age is required.")
    private Long age;

    @NotNull(message = "SocietyId is required.")
    private Long societyId;
}
