package com.twentyfour_seven.catvillage.cat.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity(name = "TAG_TO_CAT")
@NoArgsConstructor
@AllArgsConstructor
@IdClass(TagToCatId.class)
public class TagToCat {
    @Id
    @ManyToOne
    @JoinColumn(name = "TAG_ID")
    @JsonBackReference
    private CatTag catTag;

    @Id
    @ManyToOne
    @JoinColumn(name = "CAT_ID")
    @JsonBackReference
    private Cat cat;
}
