package com.example.Demo3.dtos;

import com.example.Demo3.entities.City;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AreaDto {

    @NotNull(message = "Area Id is required.")
    private Long areaId;

    @NotNull(message = "Area name is required.")
    @NotBlank(message = "Area name is required.")
    private String areaName;

    @NotNull(message = "City Id is required.")
    @NotBlank(message = "City Id is required.")
    private CityDto cityDto;
}
