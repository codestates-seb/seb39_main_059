package com.twentyfour_seven.catvillage.cat.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity(name = "BREED")
@NoArgsConstructor
public class Breed {
    @Id
    @Column(name = "BREED_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long breedId;

    @Column(name = "KOR_NAME", nullable = false)
    private String korName;

    @Column(name = "ENG_NAME")
    private String engName;

    @OneToMany(mappedBy = "breed")
    @JsonManagedReference
    private List<Cat> cats = new ArrayList<>();
}
