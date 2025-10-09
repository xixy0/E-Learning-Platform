package com.internshipProject1.LearningPLatform.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        final String authHead =  request.getHeader("Authorization");
        final String username;
        final String token;


        if(authHead == null || !authHead.startsWith("Bearer ")){
           System.out.println("JWTAuthenticationFilter triggered for: " + request.getRequestURI());
           filterChain.doFilter(request,response);
           return;
        }

        token = authHead.substring(7);
        username = jwtService.extractUsername(token);


        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);


            if(jwtService.isTokenValid(token,userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken
                        = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                System.out.println("Authentication set for: " + username);
            }
        }

      filterChain.doFilter(request,response);
    }
}
