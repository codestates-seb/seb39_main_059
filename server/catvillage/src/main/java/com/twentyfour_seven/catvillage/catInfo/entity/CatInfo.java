package com.twentyfour_seven.catvillage.catInfo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.twentyfour_seven.catvillage.cat.entity.Cat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "CAT_INFO")
@Getter
@NoArgsConstructor
public class CatInfo {
    @Id
    @Column(name = "CAT_INFO_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long catInfoId;

    @Column(name = "KOR_NAME", nullable = false, length = 20, unique = true)
    private String korName;

    @Column(name = "ENG_NAME", length = 20)
    private String engName;

    @Column(name = "CHARACTERS", length = 1000)
    private String character;

    @Column(name = "HAIR_LENGTH")
    private Short hairLength;

    @Column(name = "HAIR_LOSS")
    private Short hairLoss;

    @Column(name = "FEATURES", length = 1000)
    private String features;

    @Column(name = "SHOW_CAT_INFO", nullable = false)
    private boolean showCatInfo;

    @Column(name = "PICTURE")
    private String picture;

    @OneToMany(mappedBy = "catInfo")
    @JsonManagedReference
    List<Cat> cats = new ArrayList<>();

    @OneToMany(mappedBy = "catInfo")
    @JsonManagedReference
    List<DiseaseToCat> diseaseToCats = new ArrayList<>();

    @Builder
    public CatInfo(String korName, String engName, String character, Short hairLength, Short hairLoss,
                   String features, boolean showCatInfo, String picture) {
        this.korName = korName;
        this.engName = engName;
        this.character = character;
        this.hairLength = hairLength;
        this.hairLoss = hairLoss;
        this.features = features;
        this.showCatInfo = showCatInfo;
        this.picture = picture;
    }
}
