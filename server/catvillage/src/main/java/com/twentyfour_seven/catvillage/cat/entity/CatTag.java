package com.twentyfour_seven.catvillage.cat.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity(name = "CAT_TAG")
@NoArgsConstructor
public class CatTag {
    @Id
    @Column(name = "TAG_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    @Column(name = "TAG_NAME", length = 15, nullable = false)
    private String tagName;

    @OneToMany(mappedBy = "catTag")
    @JsonManagedReference
    private List<TagToCat> tagToCats = new ArrayList<>();

    public CatTag(String tagName) {
        this.tagName = tagName;
    }
}
