package com.example.todo_list.security;

import com.example.todo_list.exceptions.UserNotFoundException;
import com.example.todo_list.respositories.UserEntityRepository;
import com.example.todo_list.models.UserEntity;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    UserEntityRepository userEntityRepository;

    public WebSecurityConfig(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserEntityRepository userEntityRepository) {
        return username -> {
            Optional<UserEntity> user = userEntityRepository.findByUsername(username);

            return user.orElseThrow(UserNotFoundException::new);
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/register").permitAll();
                    auth.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll();
                    auth.anyRequest().authenticated();
                });

        http.formLogin().loginPage("/login").defaultSuccessUrl("/", true).permitAll();
        http.csrf().disable();
        http.headers().frameOptions().disable();
        return http.build();
    }

}
