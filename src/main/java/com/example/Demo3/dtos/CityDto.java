package com.example.Demo3.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CityDto {

    @NotNull(message = "City Id is required.")
    private Long cityId;

    @NotNull(message = "City name is required.")
    @NotBlank(message = "City name is required.")
    private String cityName;

    @NotNull(message = "City state is required.")
    @NotBlank(message = "City state is required.")
    private String cityState;
}
