package org.example.dacn_qllh_lms.entity.authentication;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.dacn_qllh_lms.entity.User;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String accessToken;
    String refreshToken;
    boolean loggedOut;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
