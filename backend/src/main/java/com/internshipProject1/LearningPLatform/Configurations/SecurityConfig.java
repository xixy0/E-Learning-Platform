package com.internshipProject1.LearningPLatform.Configurations;

import com.internshipProject1.LearningPLatform.Security.CustomUserDetailsService;
import com.internshipProject1.LearningPLatform.Security.JWTAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
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
    private JWTAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/users/register","/api/auth/authenticate","/api/users/updateUser/**","/api/users/getLoggedInUser",
                                "/api/course/getInstructor/**","/api/course/getAll",
                                "/api/student/unenroll/**",
                                "/api/course/getAllQuiz/**",
                                "/api/submissions/getAll").permitAll()
                        .requestMatchers(
                                "/api/users/getAll","/api/users/deactivate/**","/api/users/activate/**","/api/users/deleteUser/**",
                                "/api/users/viewCoursesByAdmin/**","/api/users/viewEnrolledCoursesForAdmin/**","/api/user/getSubmissionsByAdmin/**",
                                "/api/course/removeEnroll/**").hasRole("ADMIN")
                        .requestMatchers(
                                "/api/users/viewEnrolledCourses","/api/users//getSubmissions", "/api/users/getAllStudentAssignmentSubmissions",
                                "/api/student/enroll/**",
                                "/api/submissions/**",
                                "/api/assignmentSubmissions/**",
                                "/api/lessonProgress/**").hasAnyRole("ADMIN","STUDENT")
                        .requestMatchers(
                                "/api/users/viewCourses/**","api/course/**",
                                "/api/lesson/**",
                                "/api/quiz/**",
                                "/api/questions/**",
                                "/api/assignment/**").hasAnyRole("ADMIN","INSTRUCTOR")
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
