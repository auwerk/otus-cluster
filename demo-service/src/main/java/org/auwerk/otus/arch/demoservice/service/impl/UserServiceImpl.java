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

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(UserServiceImpl::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public User getUser(Long id) throws UserServiceException {
        return entityToDto(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id)));
    }

    @Override
    public void createUser(User user) throws UserServiceException {
        try {
            UserEntity userEntity = UserEntity.fromDto(user);
            userRepository.save(userEntity);
            user.setId(userEntity.getId());
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

    private static User entityToDto(UserEntity userEntity) {
        return new User(userEntity.getId(), userEntity.getUsername(), userEntity.getFirstName(),
                userEntity.getLastName(), userEntity.getEmail(), userEntity.getPhone());
    }
}
