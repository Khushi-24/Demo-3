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
public class CompanyEmployeeDto {

    private Long companyEmployeeId;

    @NotNull(message = "Member Id is required.")
    private MemberDto memberDto;

    @NotNull(message = "Company Id is required.")
    private CompanyDto companyDto;

    @NotNull(message = "Designation is required.")
    @NotBlank(message = "Designation is required.")
    private String designation;

    @NotNull(message = "Salary is required.")
    @NotBlank(message = "Salary is required.")
    private Long salary;

    private Long aggregatedSalary;

    private String employeeName;

    public CompanyEmployeeDto(Long companyEmployeeId, String employeeName, Long salary) {
        this.companyEmployeeId = companyEmployeeId;
        this.employeeName = employeeName;
        this.salary = salary;
    }

    public CompanyEmployeeDto(Long companyEmployeeId, String employeeName) {
        this.companyEmployeeId = companyEmployeeId;
        this.employeeName = employeeName;
    }
}
