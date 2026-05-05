package com.microservice.userservice.security;

public enum AuthorityEnum {
    READ_AUTHORITY("user:read"),
    WRITE_AUTHORITY("user:write"),
    ADMIN_READ_AUTHORITY("admin:read"),
    ADMIN_WRITE_AUTHORITY("admin:write")
    ;

    private final String authority;

    AuthorityEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }
}
