package com.example.Demo3.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Members")
@Getter
@Setter
public class Members {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column
    private String memberName;

    @Column
    private Long memberAge;

    @Column
    private Boolean isWorking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "familyId", referencedColumnName = "familyId")
    private Family family;

    @OneToMany(mappedBy = "members",cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<CompanyEmployee> companyEmployeeSet = new HashSet<>();

}
