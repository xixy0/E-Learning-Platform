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
public class StudentEnrollmentDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 7L;

    private Long enrollmentId;
    private LocalDate enrollmentDate;
    private Long userId;
    private Long courseId;

}
