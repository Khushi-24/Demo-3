package com.example.Demo3.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "Family")
@Getter
@Setter
public class Family {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long familyId;

    private Long familyMembers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "societyId", referencedColumnName = "societyId")
    private Society society;
}
