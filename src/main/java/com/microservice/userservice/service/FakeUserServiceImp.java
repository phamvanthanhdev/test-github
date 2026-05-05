package com.microservice.userservice.service;

import com.google.common.collect.Lists;
import com.microservice.userservice.dto.Student;
import com.microservice.userservice.security.CustomUserDetails;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.microservice.userservice.security.UserAuthorities.ADMIN_AUTHORITIES;
import static com.microservice.userservice.security.UserAuthorities.ADMIN_TRAINEE_AUTHORITIES;
import static com.microservice.userservice.security.UserAuthorities.USER_AUTHORITIES;

@Service
public class FakeUserServiceImp implements FakeUserService {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FakeUserServiceImp(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<CustomUserDetails> getUserByUsername(String username) {
        return getApplicationUsers()
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }

    private List<CustomUserDetails> getApplicationUsers() {
        CustomUserDetails user = new CustomUserDetails("user",
                passwordEncoder.encode("password"), USER_AUTHORITIES.getSetSimpleAuthorities());
        CustomUserDetails admin = new CustomUserDetails("admin",
                passwordEncoder.encode("password"), ADMIN_AUTHORITIES.getSetSimpleAuthorities());
        CustomUserDetails adminTrainee = new CustomUserDetails("trainee",
                passwordEncoder.encode("password"), ADMIN_TRAINEE_AUTHORITIES.getSetSimpleAuthorities());

        return Arrays.asList(user, admin, adminTrainee);
    }
}
