package com.example.Demo3.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RequestDtoForGettingEmployeesByAreaId {

    @NotNull(message = "Salary is required")
    private Long salary;

    @NotNull(message = "AreaId is required")
    private Long areaId;
}
