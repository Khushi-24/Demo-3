package com.example.Demo3.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RequestDtoForGettingEmployeesByCityId {

    @NotNull(message = "Salary is required")
    private Long salary;

    @NotNull(message = "cityId is required")
    private Long cityId;
}
