package com.internshipProject1.LearningPLatform.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LessonProgressDTO {

    private Long userId;
    private Long lessonId;
    private float playedTime;
    private float duration;
}
