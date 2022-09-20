package com.twentyfour_seven.catvillage.exception;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member not found"),
    MEMBER_EMAIL_EXISTS(409, "Email address already in use"),
    MEMBER_NAME_EXISTS(409, "Name already in use");

    @Getter
    private final int status;

    @Getter
    private final String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
