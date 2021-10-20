package com.example.Demo3.dtos;

import com.example.Demo3.entities.User;
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
public class CompanyDto {

    private Long companyId;

    @NotNull(message = "Company name is required.")
    @NotBlank(message = "Company name is required.")
    private String companyName;

    private String adminName;

    private String adminEmail;

    @NotNull(message = "Area Id is required.")
    private AreaDto areaDto;

    @NotNull(message = "User details is required.")
    private User user;

    public CompanyDto(Long companyId, String companyName) {
        this.companyId = companyId;
        this.companyName = companyName;
    }
}
