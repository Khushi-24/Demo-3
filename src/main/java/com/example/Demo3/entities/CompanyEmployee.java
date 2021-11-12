package com.example.Demo3.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "CompanyEmployee")
@Getter
@Setter
public class CompanyEmployee extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyEmployeeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId", referencedColumnName = "memberId")
    private Members members;

    @Column(name = "memberId", insertable = false, updatable = false, nullable = false)
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyId", referencedColumnName = "companyId")
    private Company company;

    @Column(name = "companyId", insertable = false, updatable = false, nullable = false)
    private Long companyId;

    @Column
    private String designation;

    @Column
    private Long salary;

    @Column
    private Long aggregatedSalary;

}
