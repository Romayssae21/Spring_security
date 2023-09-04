package com.Romayssae.Security.config;

import com.Romayssae.Security.repositories.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor //In case we need to inject something
public class ApplicationConfig {
    private final Repository userRepository;
    @Bean
    public UserDetailsService userDetailsService(){
        //Fetch the user from Database
        return username -> userRepository.findByEmail(username)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
    }
}
