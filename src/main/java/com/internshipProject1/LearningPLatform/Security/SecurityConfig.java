package com.internshipProject1.LearningPLatform.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private  JWTAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private  CustomUserDetailsService customUserDetailsService;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "api/users/register","api/auth/authenticate","api/users/updateUser/**",
                                "api/course/getInstructor/**","api/course/getAll",
                                "api/student/unenroll/**",
                                "api/course/getLessons/**",
                                "api/submissions/getAll").permitAll()
                        .requestMatchers(
                                "api/users/getAll","api/users/deactivate/**","api/users/activate/**","api/users/deleteUser/**",
                                "api/course/removeEnroll/**").hasRole("ADMIN")
                        .requestMatchers(
                                "api/users/viewEnrolledCourses/**",
                                "api/student/enroll/**",
                                 "api/submissions/**",
                                "api/assignmentSubmissions/**").hasAnyRole("ADMIN","STUDENT")
                        .requestMatchers(
                                "api/users/viewCourses/**","api/course/**",
                                "api/lesson/**",
                                "api/quiz/**",
                                "api/questions/**",
                                "api/assignment/**").hasAnyRole("ADMIN","INSTRUCTOR")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

}
