package com.twentyfour_seven.catvillage.cat.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Entity(name = "TAG_TO_CAT")
@NoArgsConstructor
@AllArgsConstructor
public class TagToCat {
    @ManyToOne
    @JoinColumn(name = "TAG_ID")
    @JsonBackReference
    private CatTag catTag;

    @ManyToOne
    @JoinColumn(name = "CAT_ID")
    @JsonBackReference
    private Cat cat;
}
