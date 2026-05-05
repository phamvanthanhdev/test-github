package com.microservice.userservice.security;

import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.microservice.userservice.security.AuthorityEnum.*;
import static com.microservice.userservice.security.UserAuthorities.*;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .antMatchers(HttpMethod.GET, "/api/student/**").hasAnyAuthority(READ_AUTHORITY.getAuthority(), WRITE_AUTHORITY.getAuthority())
                .antMatchers(HttpMethod.GET, "/management/api/student/**").hasAnyAuthority(ADMIN_READ_AUTHORITY.getAuthority(), ADMIN_WRITE_AUTHORITY.getAuthority())
                .antMatchers(HttpMethod.POST, "/management/api/student/**").hasAuthority(ADMIN_WRITE_AUTHORITY.getAuthority())
                .antMatchers(HttpMethod.PUT, "/management/api/student/**").hasAuthority(ADMIN_WRITE_AUTHORITY.getAuthority())
                .antMatchers(HttpMethod.DELETE, "/management/api/student/**").hasAuthority(ADMIN_WRITE_AUTHORITY.getAuthority())
                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login").permitAll()
                    .usernameParameter("username").passwordParameter("password")
                    .defaultSuccessUrl("/courses", true)
                .and()
                .rememberMe()
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                    .key("somethingverysecured")
                    .rememberMeParameter("remember-me")
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID", "remember-me")
                    .logoutSuccessUrl("/login");
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        UserDetails user = new CustomUserDetails("user",
                passwordEncoder().encode("password"), USER_AUTHORITIES.getSetSimpleAuthorities());
        UserDetails admin = new CustomUserDetails("admin",
                passwordEncoder().encode("password"), ADMIN_AUTHORITIES.getSetSimpleAuthorities());
        UserDetails adminTrainee = new CustomUserDetails("trainee",
                passwordEncoder().encode("password"), ADMIN_TRAINEE_AUTHORITIES.getSetSimpleAuthorities());

        return new InMemoryUserDetailsManager(user, admin, adminTrainee);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
