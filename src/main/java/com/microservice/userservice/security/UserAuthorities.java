package com.microservice.userservice.security;

import com.google.common.collect.Sets;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static com.microservice.userservice.security.AuthorityEnum.*;

public enum UserAuthorities {
    USER_AUTHORITIES(Sets.newHashSet(READ_AUTHORITY.getAuthority(), WRITE_AUTHORITY.getAuthority())),
    ADMIN_AUTHORITIES(Sets.newHashSet(ADMIN_READ_AUTHORITY.getAuthority(), ADMIN_WRITE_AUTHORITY.getAuthority())),
    ADMIN_TRAINEE_AUTHORITIES(Sets.newHashSet(ADMIN_READ_AUTHORITY.getAuthority())),
    ;

    private final Set<String> authorities;

    UserAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public Set<SimpleGrantedAuthority> getSetSimpleAuthorities() {
        return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }
}
