package com.example.Demo3.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "family", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Members> members = new HashSet<>();
}
