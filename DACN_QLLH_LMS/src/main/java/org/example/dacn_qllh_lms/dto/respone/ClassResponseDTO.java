package org.example.dacn_qllh_lms.dto.respone;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClassResponseDTO {
    Long classId;
    String className;
    String classCode;
    String description;
    LocalDate startDate;
    LocalDate endDate;
    Long teacherId;
    String teacherName;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
