package com.mysite.library;

import lombok.Getter;

@Getter
public enum UserRole {
    //이넘으로 관리자, 유저
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private final String value;

    UserRole(String value) {
        this.value = value;
    }


}
