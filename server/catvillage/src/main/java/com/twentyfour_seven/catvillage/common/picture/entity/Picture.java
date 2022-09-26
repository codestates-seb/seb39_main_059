package com.twentyfour_seven.catvillage.common.picture.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.twentyfour_seven.catvillage.board.entity.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity(name = "PICTURE")
@Getter
@NoArgsConstructor
public class Picture {
    @Id
    @Column(name = "PICTURE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pictureId;

    // TODO: Feed entity 추가 후 주석 제거 필요
//    @ManyToOne
//    @JoinColumn(name = "FEED_ID")
//    @JsonBackReference
//    private Feed feedId;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    @JsonBackReference
    private Board boardId;

    @Column(name = "PATH", length = 100)
    private String path;

    @Builder
    public Picture(Long pictureId, String path) {
        this.pictureId = pictureId;
        this.path = path;
    }
}
