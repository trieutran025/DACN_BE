package org.example.dacn_qllh_lms.service.Interface;


import org.example.dacn_qllh_lms.dto.request.ChangePasswordRequest;
import org.example.dacn_qllh_lms.dto.respone.ChangePasswordResponse;
import org.example.dacn_qllh_lms.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.stereotype.Service;

@Service
public interface UserService extends UserDetailsService {
    User getCurrentUser();


    ChangePasswordResponse changePassword(ChangePasswordRequest changePasswordRequest);
}
