package com.example.Demo3.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RequestDtoForEmployeeIdAndCompanyId {

    @NotNull(message = "EmployeeId is required.")
    private Long employeeId;

    @NotNull(message = "CompanyId is required.")
    private Long companyId;
}
