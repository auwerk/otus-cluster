package org.auwerk.otus.arch.demoservice.service.exception;

public class UserServiceException extends Exception {
    public UserServiceException(String message) {
        super(message);
    }

    public UserServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
