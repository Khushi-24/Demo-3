package com.example.Demo3.dtos;

import com.example.Demo3.entities.Area;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SocietyDto {

    private Long societyId;

    @NotNull(message = "Society name is required.")
    @NotBlank(message = "Society name is required.")
    private String societyName;

    @NotNull(message = "Society name is required.")
    @NotBlank(message = "Society name is required.")
    private String societyAddress;

    @NotNull(message = "Area Id is required.")
    private AreaDto areaDto;

    public SocietyDto(Long societyId, String societyName) {
        this.societyId = societyId;
        this.societyName = societyName;
    }
}
