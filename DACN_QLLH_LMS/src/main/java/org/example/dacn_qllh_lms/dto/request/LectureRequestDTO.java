package org.example.dacn_qllh_lms.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LectureRequestDTO {
    Long lectureId;
    Long classId;
    String title;
    String content;
    LocalDate lectureDate;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
