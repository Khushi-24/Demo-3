package com.example.Demo3.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "societyAdmin", referencedColumnName = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "areaId", referencedColumnName = "areaId")
    private Area area;
}
