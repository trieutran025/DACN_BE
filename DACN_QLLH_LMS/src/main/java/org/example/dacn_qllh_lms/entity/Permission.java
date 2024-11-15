package org.example.dacn_qllh_lms.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long permissionId;

     String permissionName;

    @ManyToOne
    @JoinColumn(name = "role_id")
     Role role;
}
