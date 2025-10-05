package com.internshipProject1.LearningPLatform.Entity;

import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.time.LocalDate;

public class Users {
    String firstName;
    String middleName;
    String LastName;
    String userPhone;
    LocalDate userDOB;
    String address;
    String email;
    String gender;

    @OneToOne
    private Login login;

    @OneToMany
    private StudentEnrollment studentEnrollment;

    @OneToMany
    private Course course;

    @OneToMany



}
