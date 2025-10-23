package com.internshipProject1.LearningPLatform.Security;

import com.internshipProject1.LearningPLatform.Entity.Login;
import com.internshipProject1.LearningPLatform.Repository.LoginRepository;
//import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private LoginRepository loginRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Login login = loginRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not Found"));

        if(!login.getAccountStatus().equalsIgnoreCase("ACTIVE")){

                throw new UsernameNotFoundException("Account not active");

        }
        System.out.println("Loaded user: " + login.getUsername());
        System.out.println("Role: " + login.getRole());
        System.out.println("Authorities: " + Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + login.getRole())));

        return new User(
                login.getUsername(),
                login.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + login.getRole()))
        );

    }
}
