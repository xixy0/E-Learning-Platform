package com.internshipProject1.LearningPLatform.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "login")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="login_id")
    Long loginId;

    @Column(name = "username",unique = true,nullable = false)
    String username;

    @Column(name = "password",nullable = false)
    String password;

    @Column(name="role",nullable = false)
    String role;

    @Column(name= "account_Status",nullable = false)
    String accountStatus;

    @OneToOne(mappedBy = "login")
    @JsonBackReference
    private Users users;


}
