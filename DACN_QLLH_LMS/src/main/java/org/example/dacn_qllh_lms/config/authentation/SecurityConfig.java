package org.example.dacn_qllh_lms.config.authentation;

import jakarta.annotation.Resource;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class SecurityConfig {
    @Resource
    UserDetailsService userDetailsService;

    JwtAuthenticationFilter jwtAuthenticationFilter;

    CustomLogoutHandler logoutHandler;
    private static final String[] PUBLIC_ENDPOINTS = {"/login/**", "/home/**", "/customer/register"};
    private static final String[] ADMIN_ENDPOINTS = {"/api/account/**","/api/employee/**",};
    private static final String[] MANAGER_ENDPOINTS = {"/api/manager/update/**","/api/receptionist/**"};
    private static final String[] RECEPTIONIST_ENDPOINTS = {"/api/receptionist/update/**"};
    private static final String[] CUSTOMER_ENDPOINTS = {"/api/aaa/**"};




    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                        .requestMatchers(ADMIN_ENDPOINTS).hasRole("ADMIN")
                        .requestMatchers(MANAGER_ENDPOINTS).hasRole("MANAGER")
                        .requestMatchers(RECEPTIONIST_ENDPOINTS).hasRole("RECEPTIONIST")
                        .requestMatchers(CUSTOMER_ENDPOINTS).hasRole("CUSTOMER")
                        .anyRequest().authenticated()

                ).userDetailsService(userDetailsService)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(e -> e
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                            System.out.println("Current Authorities: " + authentication.getAuthorities());
                            response.setStatus(HttpStatus.FORBIDDEN.value());
                            System.out.println("Access Denied: " + accessDeniedException.getMessage());
                            System.out.println("User: " + authentication.getName() + " - Roles: " + authentication.getAuthorities());
                            try {
                                if (!authentication.getAuthorities().isEmpty()) {
                                    System.out.println("Authorities are present.");
                                } else {
                                    System.out.println("No authorities found. Please check role mapping in buildScope.");
                                }
                            } catch (Exception r) {
                                System.out.println("Error: " + r.getMessage());
                            }

                        })
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                )
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }


}
