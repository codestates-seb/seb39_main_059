package com.twentyfour_seven.catvillage.catInfo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiseaseToCatId implements Serializable {
    private Long catInfo;
    private Long disease;
}
