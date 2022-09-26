package com.twentyfour_seven.catvillage.cat.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagToCatId implements Serializable {
    private CatTag catTag;
    private Cat cat;
}
