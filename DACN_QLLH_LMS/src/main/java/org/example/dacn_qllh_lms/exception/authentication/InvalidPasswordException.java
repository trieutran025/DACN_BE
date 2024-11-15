package org.example.dacn_qllh_lms.exception.authentication;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
