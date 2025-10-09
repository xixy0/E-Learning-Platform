package com.internshipProject1.LearningPLatform.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@RequiredArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "first_name",nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name",nullable = false)
    private String lastName;

    @Column(name = "phone",nullable = false)
    private String userPhone;



    @Column(name = "DOB")
    private LocalDate userDOB;

    @Column(name = "address",nullable = false)
    private String address;

    @Column(name = "email",nullable = false)
    private String email;

    @Column(name = "gender",nullable = false)
    private String gender;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "login_id",referencedColumnName = "login_id")
    @JsonManagedReference
    private Login login;

//    @OneToMany(mappedBy = "users",cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<StudentEnrollment> studentEnrollments;
//
//    @OneToMany(mappedBy = "users",cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<Course> courses;
//
//    @OneToMany(mappedBy = "users" , cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<Submission> submissions;


    public Users() {
    }

    public Users(Long userId, String firstName, String middleName, String lastName, String userPhone, LocalDate userDOB, String address, String email, String gender, Login login) {
        this.userId = userId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.userPhone = userPhone;
        this.userDOB = userDOB;
        this.address = address;
        this.email = email;
        this.gender = gender;
        this.login = login;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public LocalDate getUserDOB() {
        return userDOB;
    }

    public void setUserDOB(LocalDate userDOB) {
        this.userDOB = userDOB;
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

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }
}
