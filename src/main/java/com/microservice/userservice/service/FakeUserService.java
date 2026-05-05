package com.microservice.userservice.service;

import com.microservice.userservice.dto.Student;
import com.microservice.userservice.security.CustomUserDetails;
import java.util.Optional;

public interface FakeUserService {
    Optional<CustomUserDetails> getUserByUsername(String username);
}
