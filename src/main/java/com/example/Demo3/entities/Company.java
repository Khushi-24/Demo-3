package com.example.Demo3.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Company")
@Getter
@Setter
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;

    @Column
    private String companyName;

    @Column
    private String adminName;

    @Column
    private String adminEmail;

    @ManyToOne
    @JoinColumn(name = "location", referencedColumnName = "areaId")
    private Area area;

    @OneToMany(mappedBy = "company",cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<CompanyEmployee> companyEmployeeSet = new HashSet<>();
}
