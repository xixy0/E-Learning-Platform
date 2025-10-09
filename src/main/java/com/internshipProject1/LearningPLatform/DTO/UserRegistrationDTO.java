package com.internshipProject1.LearningPLatform.DTO;

//import lombok.Data;

import java.time.LocalDate;

//@Data
public class UserRegistrationDTO {

    private String username;
    private String password;
    private String role;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNum;
    private LocalDate userDOB;
    private String address;
    private String email;
    private String gender;

    public UserRegistrationDTO(String username, String password, String role, String firstName, String middleName, String lastName, String phoneNum, LocalDate userDOB, String address, String email, String gender) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phoneNum = phoneNum;
        this.userDOB = userDOB;
        this.address = address;
        this.email = email;
        this.gender = gender;
    }

    public UserRegistrationDTO() {
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public LocalDate getUserDOB() {
        return userDOB;
    }

    public void setUserDOB(LocalDate DOB) {
        this.userDOB = DOB;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
