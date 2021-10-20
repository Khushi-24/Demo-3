package com.example.Demo3.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RequestDtoToGetEmployeeListByKeywordAndCompanyId {

    @NotNull(message = "Keyword is required.")
    @NotBlank(message = "Keyword is required.")
    private String keyword;

    @NotNull(message = "CompanyId is required")
    private Long companyId;

    @NotNull(message = "PageNo is required.")
    private int pageNo;
}
