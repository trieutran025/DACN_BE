package org.example.dacn_qllh_lms.dto.respone;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponseDTO {
    Long userId;
    String username;
    String email;
    String fullName;
    String phoneNumber;
    String role;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
