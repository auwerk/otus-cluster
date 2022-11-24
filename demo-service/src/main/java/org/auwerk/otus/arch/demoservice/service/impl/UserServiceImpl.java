package org.auwerk.otus.arch.demoservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.auwerk.otus.arch.demoservice.api.dto.User;
import org.auwerk.otus.arch.demoservice.entity.UserEntity;
import org.auwerk.otus.arch.demoservice.repository.UserRepository;
import org.auwerk.otus.arch.demoservice.service.UserService;
import org.auwerk.otus.arch.demoservice.service.exception.UserNotFoundException;
import org.auwerk.otus.arch.demoservice.service.exception.UserServiceException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUser(Long id) throws UserServiceException {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        return null;
    }

    @Override
    public void createUser(User user) throws UserServiceException {
        try {
            UserEntity userEntity = UserEntity.fromDto(user);
            userRepository.save(userEntity);
        } catch (DataIntegrityViolationException ex) {
            throw new UserServiceException("failed to create user", ex);
        }
    }

    @Override
    public void deleteUser(Long id) throws UserServiceException {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }
}
