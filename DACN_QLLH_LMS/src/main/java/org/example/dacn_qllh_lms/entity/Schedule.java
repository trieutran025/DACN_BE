package org.example.dacn_qllh_lms.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.security.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long scheduleId;

     String lessonTitle;
     Timestamp startTime;
     Timestamp endTime;
     String location;

    @ManyToOne
    @JoinColumn(name = "class_id")
     Class classEntity;

    @CreationTimestamp
     Timestamp createdAt;
}
