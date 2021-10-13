package com.example.Demo3.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Area")
@Getter
@Setter
public class Area {

    @Id
    private Long areaId;

    @Column
    private String areaName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cityId", referencedColumnName = "cityId")
    private City city;

    @OneToMany(mappedBy = "area", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Society> societies = new HashSet<>();
}
