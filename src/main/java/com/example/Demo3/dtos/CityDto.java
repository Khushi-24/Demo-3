package com.example.Demo3.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CityDto {

    @NotNull(message = "City Id is required.")
    private Long cityId;

    @NotNull(message = "City name is required.")
    @NotBlank(message = "City name is required.")
    private String cityName;

    @NotNull(message = "City state is required.")
    @NotBlank(message = "City state is required.")
    private String cityState;

    public CityDto(Long cityId, String cityName) {
        this.cityId = cityId;
        this.cityName = cityName;
    }
}
