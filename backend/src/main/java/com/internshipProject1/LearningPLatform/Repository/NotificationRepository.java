package com.internshipProject1.LearningPLatform.Repository;

import com.internshipProject1.LearningPLatform.Entity.Notification;
import com.internshipProject1.LearningPLatform.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findByUserOrderByCreatedAtDesc(Users user);
    List<Notification> findByUserAndReadFlagFalse(Users user);
}
