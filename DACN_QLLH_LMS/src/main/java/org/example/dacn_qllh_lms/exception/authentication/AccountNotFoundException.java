package org.example.dacn_qllh_lms.exception.authentication;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String message) {
        super(message);
    }
}