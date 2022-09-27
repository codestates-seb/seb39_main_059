package com.twentyfour_seven.catvillage.exception;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member not found"),
    MEMBER_EMAIL_EXISTS(409, "Email address already in use"),
    MEMBER_NAME_EXISTS(409, "Name already in use"),
    BREED_NOT_FOUND(404, "Breed not found"),
    CAT_NOT_FOUND(409, "Cat not found"),
    PICTURE_NOT_FOUND(409, "Picture not found"),
    INVALID_USER(405, "Method not allowed");

    @Getter
    private final int status;

    @Getter
    private final String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
