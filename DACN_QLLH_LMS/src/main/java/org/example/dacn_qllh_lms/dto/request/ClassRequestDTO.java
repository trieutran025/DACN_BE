package org.example.dacn_qllh_lms.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClassRequestDTO {
    String className;
    String classCode;
    String description;
    LocalDate startDate;
    LocalDate endDate;
    Long teacherId;

}
