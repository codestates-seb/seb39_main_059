package com.twentyfour_seven.catvillage.catInfo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "DISEASE_TO_CAT")
@Getter
@NoArgsConstructor
@IdClass(DiseaseToCatId.class)
public class DiseaseToCat {
    @Id
    @ManyToOne
    @JoinColumn(name = "CAT_INFO_ID")
    @JsonBackReference
    private CatInfo catInfo;

    @Id
    @ManyToOne
    @JoinColumn(name = "DISEASE_ID")
    @JsonBackReference
    private Disease disease;

    @Builder
    public DiseaseToCat(CatInfo catInfo, Disease disease) {
        this.catInfo = catInfo;
        this.disease = disease;
    }
}
