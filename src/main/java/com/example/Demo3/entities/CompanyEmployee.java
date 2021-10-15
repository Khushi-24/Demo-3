package com.example.Demo3.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "CompanyEmployee")
@Getter
@Setter
public class CompanyEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyEmployeeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId", referencedColumnName = "memberId")
    private Members members;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyId", referencedColumnName = "companyId")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "areaId", referencedColumnName = "areaId")
    private Area area;

    @ManyToOne(fetch = FetchType.LAZY)
    private City city;

    @Column
    private String designation;

    @Column
    private Long salary;

    @Column
    private Long aggregatedSalary;

    @Column
    private String employeeName;

}
