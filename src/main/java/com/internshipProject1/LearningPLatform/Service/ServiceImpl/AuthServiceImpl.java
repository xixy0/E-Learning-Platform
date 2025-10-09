package com.internshipProject1.LearningPLatform.Service.ServiceImpl;

import com.internshipProject1.LearningPLatform.DTO.AuthRequest;
import com.internshipProject1.LearningPLatform.DTO.AuthResponse;
import com.internshipProject1.LearningPLatform.Security.CustomUserDetailsService;
import com.internshipProject1.LearningPLatform.Security.JwtService;
import com.internshipProject1.LearningPLatform.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtService jwtService;

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        try{
           authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
        }
        catch (Exception ex){
            throw new RuntimeException("Invalid Credentials");
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtService.generateToken(userDetails);
        return new AuthResponse(token);
    }
}
