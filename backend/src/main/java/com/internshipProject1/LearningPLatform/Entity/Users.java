package com.internshipProject1.LearningPLatform.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Users  {

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

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "login_id",referencedColumnName = "login_id")
    private Login login;

    @OneToMany(mappedBy = "instructor",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private List<Course> courses;

    @OneToMany(mappedBy = "users",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private List<StudentEnrollment> studentEnrollments;

    @OneToMany(mappedBy = "student" , cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private List<Submission> submissions;

    @OneToMany(mappedBy = "users",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private List<AssignmentSubmission> assignmentSubmissions;

}
