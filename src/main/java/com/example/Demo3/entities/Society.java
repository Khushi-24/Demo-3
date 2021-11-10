package com.example.Demo3.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "Society")
@Getter
@Setter
public class Society {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long societyId;

    @Column
    private String societyName;

    @Column
    private String societyAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "areaId", referencedColumnName = "areaId")
    private Area area;

    @Column(name = "areaId", insertable = false, updatable = false, nullable = false)
    private Long areaId;

    @Column
    private String societyAdminEmail;
}
