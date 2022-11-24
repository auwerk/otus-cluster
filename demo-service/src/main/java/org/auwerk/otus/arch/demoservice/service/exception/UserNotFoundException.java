package org.auwerk.otus.arch.demoservice.service.exception;

public class UserNotFoundException extends UserServiceException {
    public UserNotFoundException(Long id) {
        super(String.format("user not found, id=%1$d", id));
    }
}
