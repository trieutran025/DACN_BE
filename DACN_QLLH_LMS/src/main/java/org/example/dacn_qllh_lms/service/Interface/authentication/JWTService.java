package org.example.dacn_qllh_lms.service.Interface.authentication;


import io.jsonwebtoken.Claims;
import org.example.dacn_qllh_lms.entity.User;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Function;

public interface JWTService {
    String extractUsername(String token);
    boolean isValid(String token, UserDetails userDetails);
    boolean isValidRefreshToken(String token, User user);
    <T> T extractClaim(String token, Function<Claims,T> resolver);
    String generateAccessToken(User user);
    String generateRefreshToken(User user);
    String[] getRolesFromToken(String token);
}
