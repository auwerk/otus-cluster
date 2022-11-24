package org.auwerk.otus.arch.demoservice.service;

import org.auwerk.otus.arch.demoservice.api.dto.User;
import org.auwerk.otus.arch.demoservice.service.exception.UserServiceException;

import java.util.List;

public interface UserService {

    List<User> getUsers();

    User getUser(Long id) throws UserServiceException;

    void createUser(User user) throws UserServiceException;

    void deleteUser(Long id) throws UserServiceException;
}
