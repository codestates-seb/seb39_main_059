package com.twentyfour_seven.catvillage.exception;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member not found"),
    MEMBER_EMAIL_EXISTS(409, "Email address already in use"),
    MEMBER_NAME_EXISTS(409, "Name already in use"),
    BREED_NOT_FOUND(404, "Breed not found"),
    BREED_EXISTS(409, "Breed already exists"),
    CAT_NOT_FOUND(404, "Cat not found"),
    PICTURE_NOT_FOUND(404, "Picture not found"),
    BOARD_NOT_FOUND(404, "Board not found"),
    BOARD_TAG_NOT_FOUND(404, "Board tag not found"),
    INVALID_USER(405, "Method not allowed"),
    FEED_NOT_FOUND(404, "Feed not found"),
    BOARD_COMMENT_NOT_FOUND(404, "Board comment not found"),
    COMMENT_NOT_FOUND(404, "Comment not found"),
    REFRESH_TOKEN_NOT_FOUND(404, "Refresh token not found"),
    INTERNAL_SERVER_ERROR(500, "Internal server error"),
    LIKE_EXISTS(409, "Like already exists"),
    LIKE_NOT_FOUND(404, "Like not found"),
    FOLLOWER_NOT_FOUND(404, "Follower not found"),
    FOLLOWER_EXISTS(409, "Follower already exists");

    @Getter
    private final int status;

    @Getter
    private final String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
