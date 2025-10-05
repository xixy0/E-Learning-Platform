package com.internshipProject1.LearningPLatform.Entity;

import jakarta.persistence.OneToOne;

public class Login {
    Long loginId;
    String username;
    String password;
    String role;
    String accountStatus;

    @OneToOne(mappedBy = "login")
    private Users users;

}
