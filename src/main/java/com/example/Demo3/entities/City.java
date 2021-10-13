package com.example.Demo3.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "City")
@Getter
@Setter
public class City {

    @Id
    private Long cityId;

    @Column
    private String cityName;

    @Column
    private String cityState;
}
