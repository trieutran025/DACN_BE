package org.example.dacn_qllh_lms.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequestDTO {
    String username;
    String email;
    String password;
    String fullName;
    String phoneNumber;
//    String role; // ADMIN, TEACHER, STUDENT
}
