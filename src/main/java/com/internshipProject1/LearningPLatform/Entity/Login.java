package com.internshipProject1.LearningPLatform.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "login")

public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="login_id")
    private Long loginId;

    @Column(name = "username",unique = true,nullable = false)
    private String username;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name="role",nullable = false)
    private String role;

    @Column(name= "account_Status",nullable = false)
    private String accountStatus;



    @OneToOne(mappedBy = "login")
    @JsonBackReference
    private Users users;

    public Login() {
    }

    public Login(Long loginId, String username, String password, String role, String accountStatus, Users users) {
        this.loginId = loginId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.accountStatus = accountStatus;
        this.users = users;
    }

    public Long getLoginId() {
        return loginId;
    }

    public void setLoginId(Long loginId) {
        this.loginId = loginId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
