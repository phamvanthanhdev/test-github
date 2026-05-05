package com.microservice.userservice.security;

import com.microservice.userservice.service.FakeUserService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private FakeUserService fakeUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<CustomUserDetails> userOptional = fakeUserService.getUserByUsername(username.toLowerCase());
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException(username);
        } else {
            return userOptional.get();
        }
    }
}
