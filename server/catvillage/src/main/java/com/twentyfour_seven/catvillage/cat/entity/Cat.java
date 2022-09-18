package com.twentyfour_seven.catvillage.cat.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "CAT")
@Getter
@NoArgsConstructor
public class Cat {
    @Id
    @Column(name = "CAT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long catId;

    @Column(name = "NAME", length = 16, nullable = false)
    private String name;

    @Column(name = "BIRTH_DATE")
    private Date birthDate;

    @Column(name = "SEX", length = 1)
    private String sex;

    @Column(name = "WEIGHT")
    private int weight;

    @Column(name = "IMAGE", length = 50)
    private String image;

    @Column(name = "BODY", length = 1000)
    private String body;

    @Setter
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @JsonBackReference
    private User user;

    @Setter
    @ManyToOne
    @JoinColumn(name = "BREED_ID")
    @JsonBackReference
    private Breed breed;

    @OneToMany(mappedBy = "cat", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<TagToCat> tagToCats = new ArrayList<>();
}
