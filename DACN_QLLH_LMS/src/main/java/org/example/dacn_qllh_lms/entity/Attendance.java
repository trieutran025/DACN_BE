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
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attendanceId;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status; // Present, Absent, Late, Excused

    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;

    @ManyToOne
    @JoinColumn(name = "class_id")
     Class classEntity;

    @ManyToOne
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @CreationTimestamp
    private Timestamp createdAt;
}
