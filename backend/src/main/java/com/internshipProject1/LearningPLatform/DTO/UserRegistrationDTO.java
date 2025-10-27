package com.internshipProject1.LearningPLatform.DTO;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDTO {

    private Long userId;
    private Long loginId;
    private String username;
    private String password;
    private String role;
    private String accountStatus;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNum;
    private LocalDate userDOB;
    private String address;
    private String email;
    private String gender;
    private Integer numberOfCoursesEnrolled;

}
