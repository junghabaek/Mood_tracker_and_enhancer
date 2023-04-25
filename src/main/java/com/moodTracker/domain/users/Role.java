package com.moodTracker.domain.users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
//@NoArgsConstructor
//@RequiredArgsConstructor
public enum Role {
    GUEST("ROLE_GUEST", "게스트"),
    USER("ROLE_USER", "회원");

    private final String key;
    private final String title;

    Role(String key, String title){
        this.key=key;
        this.title=title;
    }

}
