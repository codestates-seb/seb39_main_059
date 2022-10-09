package com.twentyfour_seven.catvillage.board.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagToBoardId implements Serializable {
    private Long board;
    private Long boardTag;
}
