package org.example.dacn_qllh_lms.config.authentation;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.example.dacn_qllh_lms.repository.Interface.UserRepository;
import org.example.dacn_qllh_lms.service.Impl.authentication.JwtServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    JwtServiceImpl jwtServiceImpl;
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(
          @NonNull HttpServletRequest request,
           @NonNull HttpServletResponse response,
          @NonNull  FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request,response);
            return;
        }

        String token = authHeader.substring(7);
        String username = jwtServiceImpl.extractUsername(token);

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userRepository.loadUserByUsername(username);

            if(jwtServiceImpl.isValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);


    }
}
