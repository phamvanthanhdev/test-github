package com.microservice.userservice.security;

import com.microservice.userservice.jwt.JwtTokenVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.microservice.userservice.security.AuthorityEnum.*;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtTokenVerifier jwtTokenVerifier;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "index", "/auth/**", "/css/*", "/js/*").permitAll()
                .antMatchers(HttpMethod.GET, "/api/student/**").hasAnyAuthority(READ_AUTHORITY.getAuthority(), WRITE_AUTHORITY.getAuthority())
                .antMatchers(HttpMethod.GET, "/management/api/student/**").hasAnyAuthority(ADMIN_READ_AUTHORITY.getAuthority(), ADMIN_WRITE_AUTHORITY.getAuthority())
                .antMatchers(HttpMethod.POST, "/management/api/student/**").hasAuthority(ADMIN_WRITE_AUTHORITY.getAuthority())
                .antMatchers(HttpMethod.PUT, "/management/api/student/**").hasAuthority(ADMIN_WRITE_AUTHORITY.getAuthority())
                .antMatchers(HttpMethod.DELETE, "/management/api/student/**").hasAuthority(ADMIN_WRITE_AUTHORITY.getAuthority())
                .anyRequest().authenticated()
                ;
        http.addFilterBefore(jwtTokenVerifier, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        return daoAuthenticationProvider;
    }
}
