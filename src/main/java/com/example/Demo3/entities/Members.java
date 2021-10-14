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

    @Column
    private String memberName;

    @Column
    private Long memberAge;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "familyId", referencedColumnName = "familyId")
    private Family family;

}
