package com.twentyfour_seven.catvillage.user.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "FOLLOW")
@Getter
@NoArgsConstructor
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FOLLOW_ID")
    private Long followId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    @JsonBackReference
    private User member;

    @ManyToOne
    @JoinColumn(name = "TARGET_ID")
    @JsonBackReference
    private User target;

    @Builder
    public Follow(Long followId, User member, User target) {
        this.followId = followId;
        this.member = member;
        this.target = target;
    }
}
