package com.internshipProject1.LearningPLatform.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 9L;

    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String gender;
}
