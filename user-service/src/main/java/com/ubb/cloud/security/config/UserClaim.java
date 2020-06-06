package com.ubb.cloud.security.config;

public enum UserClaim {

    USER_ID("userId"),
    EMAIL("email"),
    AUTHORITIES("authorities");

    private final String key;

    UserClaim(final String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
