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
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long notificationId;

    private String message;
     Boolean readStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
     User user;

    @CreationTimestamp
     Timestamp createdAt;
}
