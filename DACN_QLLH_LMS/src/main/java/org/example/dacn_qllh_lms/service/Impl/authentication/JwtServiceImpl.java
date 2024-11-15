package org.example.dacn_qllh_lms.service.Impl.authentication;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.micrometer.common.util.StringUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import org.example.dacn_qllh_lms.entity.Role;
import org.example.dacn_qllh_lms.entity.User;
import org.example.dacn_qllh_lms.repository.authentication.ITokenRepository;
import org.example.dacn_qllh_lms.service.Interface.authentication.JWTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Permission;
import java.util.Date;
import java.util.StringJoiner;
import java.util.function.Function;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class JwtServiceImpl implements JWTService {

    private static final Logger logger = LoggerFactory.getLogger(JwtServiceImpl.class);

    @NonFinal
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @NonFinal
    @Value("${application.security.jwt.access-token-expiration}")
    long accessTokenExpire;

    @NonFinal
    @Value("${application.security.jwt.refresh-token-expiration}")
    long refreshTokenExpire;

    ITokenRepository iTokenRepository;

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public boolean isValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);

        boolean validToken = iTokenRepository
                .findByAccessToken(token)
                .map(t -> !t.isLoggedOut())
                .orElse(false);

        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token) && validToken;
    }

    @Override

    public boolean isValidRefreshToken(String token, User user) {
        String username = extractUsername(token);

        boolean validRefreshToken = iTokenRepository
                .findByRefreshToken(token)
                .map(t -> !t.isLoggedOut())
                .orElse(false);

        return (username.equals(user.getUsername())) && !isTokenExpired(token) && validRefreshToken;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(getSignerKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public String generateAccessToken(User user) {
        return generateToken(user, accessTokenExpire);
    }

    @Override
    public String generateRefreshToken(User user) {
        return generateToken(user, refreshTokenExpire);
    }

    private String generateToken(User user, long expireTime) {
        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expireTime))
                .claim("scope", buildScope(user))
                .signWith(getSignerKey())
                .compact();
    }

    private SecretKey getSignerKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        int roleCount = 0;  // Đếm số lượng role
        int permissionCount = 0;  // Đếm số lượng permission
        System.out.println("Method buildScope started.");  // Log khi phương thức bắt đầu

        // Kiểm tra nếu user có roles
        if (user != null && user.getRoles() != null && !user.getRoles().isEmpty()) {
            System.out.println("User roles found, processing...");  // Log khi tìm thấy roles
            for (Role role : user.getRoles()) {
                if (role != null && StringUtils.isNotBlank(role.getRoleName())) {
                    String roleName = role.getRoleName();

                    // Kiểm tra và thêm tiền tố ROLE_ nếu chưa có
                    if (!roleName.startsWith("ROLE_")) {
                        roleName = "ROLE_" + roleName;
                    }

                    // Thêm role vào chuỗi và đếm số lượng role
                    stringJoiner.add(roleName);
                    roleCount++;

                    // Log thông tin role đã được thêm
                    System.out.println("Role added: " + roleName);

                    // Kiểm tra và thêm các permissions của role
                    if (role. != null && !role.getPermissions().isEmpty()) {
                        for (Permission permission : role.getPermissions()) {
                            if (permission != null && StringUtils.isNotBlank(permission.getName())) {
                                // Thêm permission vào chuỗi và đếm số lượng permission
                                stringJoiner.add(permission.getName());
                                permissionCount++;

                                // Log thông tin permission đã được thêm
                                System.out.println("Permission added: " + permission.getName());
                            } else {
                                System.out.println("Invalid or empty permission detected.");
                            }
                        }
                    } else {
                        System.out.println("No permissions found for role: " + role.getRoleName());
                    }
                } else {
                    System.out.println("Invalid or empty role detected.");
                }
            }
        } else {
            System.out.println("No roles found for user.");
        }

        // Log tổng số lượng role và permission đã được thêm
        System.out.println("Total roles added: " + roleCount);
        System.out.println("Total permissions added: " + permissionCount);

        // Trả về chuỗi kết quả
        return stringJoiner.toString();
    }




    @Override
    public String[] getRolesFromToken(String token) {
        Claims claims = extractAllClaims(token);
        String scope = (String) claims.get("scope");
        if (scope != null && !scope.isEmpty()) {
            return scope.split(" ");
        }
        return new String[0];  // Trả về mảng trống nếu scope không có
    }
}
