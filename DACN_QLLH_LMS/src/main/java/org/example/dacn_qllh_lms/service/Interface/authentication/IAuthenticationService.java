package org.example.dacn_qllh_lms.service.Interface.authentication;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.example.dacn_qllh_lms.dto.authentication.AuthenticationRequest;
import org.example.dacn_qllh_lms.repository.authentication.AuthenticationResponse;
import org.springframework.http.ResponseEntity;

import javax.security.auth.login.AccountNotFoundException;

public interface IAuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request) throws AccountNotFoundException;
    ResponseEntity refreshToken(HttpServletRequest request, HttpServletResponse response);

}
