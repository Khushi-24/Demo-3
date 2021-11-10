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
public class AreaDto {

    @NotNull(message = "Area Id is required.")
    private Long areaId;

    @NotNull(message = "Area name is required.")
    @NotBlank(message = "Area name is required.")
    private String areaName;

    @NotNull(message = "City Id is required.")
    @NotBlank(message = "City Id is required.")
    private Long cityId;

    private CityDto cityDto;

    public AreaDto(Long areaId, String areaName) {
        this.areaId = areaId;
        this.areaName = areaName;
    }

}
