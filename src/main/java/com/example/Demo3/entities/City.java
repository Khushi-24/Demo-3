package com.example.Demo3.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Area> areaSet = new HashSet<>();

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Company> companySet = new HashSet<>();

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<CompanyEmployee> companyEmployeeSet = new HashSet<>();
}
