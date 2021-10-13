package com.example.Demo3.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "Members")
@Getter
@Setter
public class Members {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String memberName;

    private Long memberAge;

    private Boolean isFamilyAdmin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "familyId", referencedColumnName = "familyId")
    private Family family;

}
