package com.twentyfour_seven.catvillage.user.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "FOLLOW")
@Getter
public class Follow {
    @Id
    private Long followId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    @JsonBackReference
    private User member;

    @ManyToOne
    @JoinColumn(name = "TARGET_ID")
    @JsonBackReference
    private User target;
}
