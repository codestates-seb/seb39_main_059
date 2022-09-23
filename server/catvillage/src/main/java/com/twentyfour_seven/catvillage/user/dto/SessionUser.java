package com.twentyfour_seven.catvillage.user.dto;

import com.twentyfour_seven.catvillage.user.entity.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getProfileImage();
    }
}
