package com.ubb.cloud.security.config;

public enum  RoleKey {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_PARTICIPANT("ROLE_PARTICIPANT"),
    ROLE_SPEAKER("ROLE_SPEAKER");

    private final String key;

    RoleKey(final String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
