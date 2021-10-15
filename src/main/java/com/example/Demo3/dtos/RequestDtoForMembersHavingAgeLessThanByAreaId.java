package com.example.Demo3.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RequestDtoForMembersHavingAgeLessThanByAreaId {

    @NotNull(message = "Age is required.")
    private Long age;

    @NotNull(message = "AreaId is required.")
    private Long areaId;
}
