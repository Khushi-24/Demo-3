package com.example.Demo3.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RequestDtoForMembersHavingAgeLessThanByCityId {

    @NotNull(message = "Age is required.")
    private Long age;

    @NotNull(message = "CityId is required.")
    private Long cityId;
}
