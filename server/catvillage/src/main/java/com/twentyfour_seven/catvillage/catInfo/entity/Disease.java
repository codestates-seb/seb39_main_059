package com.twentyfour_seven.catvillage.catInfo.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity(name = "DISEASE")
@Getter
public class Disease {
    @Id
    @Column(name = "DISEASE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diseaseId;

    @Column(name = "NAME", nullable = false, length = 30)
    private String name;
}
