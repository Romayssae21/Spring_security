package com.Romayssae.Security.config;

import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private UserDetailsService userDetailsService; //load user details from a data source
    @Override
    protected void doFilterInternal(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull FilterChain filterChain)
            throws ServletException, IOException {
        //Check the validity of JWT token
        final String authHeader = request.getHeader("Authorization"); //Header that contains the JWT token
        final String jwt;

        final String userEmail;

        if (authHeader==null || !authHeader.startsWith("Bearer")){
            filterChain.doFilter(request,response);
            return;
        }
        //Extract the token from the authentication header
        jwt= authHeader.substring(7); //Bearer already occupied 6

        //Extract the user email :
        // need a class that can manipulate JWT token to extract user email
        userEmail =jwtService.extractUsername(jwt);
        //Check if the user is already authenticated or not :
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication()==null){
            //If user not connected => Get it from the database
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
        }
    }
}
